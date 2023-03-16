package com.att.dao.configurations;

import com.att.data.configurations.ConfigValue;
import com.att.validation.ValidationError;
import com.att.validation.ValidationException;
import com.att.web.configuarations.ConfigurationException;
import java.util.Optional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ConfigurationDao {
    private static class IdProvider {
        private int currentId;

        public IdProvider() {
            currentId = 0;
        }

        public int getNextId() {
            return this.currentId++;
        }
    }

    /**
     * No DB, so store the configs in a map.
     */
    private final Map<String, List<ConfigValue>> currentConfigurations;
    private final IdProvider idProvider;

    public ConfigurationDao() {
        idProvider = new IdProvider();
        currentConfigurations = new HashMap<>();
    }

    public List<ConfigValue> getConfigurationsForYearMonth(String yearMonth) {
        List<ConfigValue> result = currentConfigurations.get(yearMonth);
        return result == null ? new ArrayList<>() : result;
    }

    public ConfigValue addConfiguration(String yearMonth, ConfigValue value) {
        // Some of this logic becomes a little cleaner when using a DB as we can leverage DB constraints and
        // the associated exceptions to drive messaging back to the user (ie, database integrity).
        int newId = idProvider.getNextId();
        value.setConfigId(newId);

        List<ConfigValue> current = currentConfigurations.get(yearMonth);
        if(current == null){
            List<ConfigValue> configs =  new ArrayList<>();
            configs.add(value);
            currentConfigurations.put(yearMonth,configs);
        }else{
            if(checkExist(current, value)){
                throw new ValidationException(new ValidationError("configName","Configuration value already exists"));
            }else{
                value.setConfigId(newId);
                current.add(value);
            }
        }
        return value;
    }

    private boolean checkExist(List<ConfigValue> current, ConfigValue value) {
        return current.stream().filter(p->p.getConfigName().equals(value.getConfigName())).findFirst().isPresent();
    }

    public void removeAllConfigurationsForYearMonth(String yearMonth) {
        currentConfigurations.remove(yearMonth);
    }

    public void removeAllConfigurationsForYearMonthId(String yearMonth, int id) {
        List<ConfigValue> current = currentConfigurations.get(yearMonth);
        if(current == null){
            // The UI does not allow this scenario; this demonstrates how to provide custom error messages when using CURL or Postman
            throw new ConfigurationException("Invalid Time Period");
        }else{
            Optional<ConfigValue> v = current.stream().filter(cv -> cv.getConfigId() == id).findFirst();
            v.ifPresent(current::remove);
        }
    }

}
