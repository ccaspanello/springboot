package com.att.web.configuarations;

import com.att.dao.configurations.ConfigurationDao;
import com.att.data.configurations.ConfigValue;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/configuration")
public class ConfigurationController {

  private final ConfigurationDao dao;

  @Autowired
  public ConfigurationController(ConfigurationDao dao) {
    this.dao = dao;
  }

  @RequestMapping(value = "/{yearMonthNumber}", method = RequestMethod.GET)
  @ResponseBody
  public List<ConfigValue> getConfigurationsForYearMonth(
      @PathVariable("yearMonthNumber") String yearMonth) {
    return dao.getConfigurationsForYearMonth(yearMonth);
  }

  @RequestMapping(value = "/{yearMonthNumber}", method = RequestMethod.DELETE)
  public void deleteConfigurationsForYearMonth(@PathVariable("yearMonthNumber") String yearMonth) {
    dao.removeAllConfigurationsForYearMonth(yearMonth);
  }

  @RequestMapping(value = "/{yearMonthNumber}/{id}", method = RequestMethod.DELETE)
  public void deleteConfigurationsForYearMonthId(@PathVariable("yearMonthNumber") String yearMonth,
                                                 @PathVariable("id") int id) {
    dao.removeAllConfigurationsForYearMonthId(yearMonth, id);
  }

  @RequestMapping(value = "/{yearMonthNumber}", method = {RequestMethod.POST, RequestMethod.PUT})
  public ConfigValue addConfigurationForYearMonth(
      @PathVariable("yearMonthNumber") String yearMonth,
      @Valid @RequestBody ConfigValue value) {
    return dao.addConfiguration(yearMonth, value);
  }
}
