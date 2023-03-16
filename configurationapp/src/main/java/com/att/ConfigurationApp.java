package com.att;

import com.att.dao.configurations.ConfigurationDao;
import com.att.data.configurations.ConfigValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConfigurationApp implements CommandLineRunner {

  public static void main(String[] args) {
    SpringApplication.run(ConfigurationApp.class, args);
  }

  private final ConfigurationDao dao;

  @Autowired
  public ConfigurationApp(ConfigurationDao dao) {
    this.dao = dao;
  }

  @Override
  public void run(String... args) {
    addConfigValues("012018", "A", "B", "C", "D");
    addConfigValues("022018", "A", "C", "F", "G", "H");

    // Load some data to show client side pagination:
    // When hooked up to a database we would typically do lazy loading if
    // data is unbounded and payload is large enough exceeding page load time expectations
    addConfigValues("032018", 1000);
  }

  private void addConfigValues(String timePeriod, String... values) {
    for (String value : values) {
      ConfigValue configValue = new ConfigValue();
      configValue.setConfigName(value);
      dao.addConfiguration(timePeriod, configValue);
    }
  }
  private void addConfigValues(String timePeriod, int count) {
    for (int i=1; i <= count; i++) {
      ConfigValue configValue = new ConfigValue();
      configValue.setConfigName("Value"+i);
      dao.addConfiguration(timePeriod, configValue);
    }
  }
}
