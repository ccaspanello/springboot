export type ConfigValue = {
  configId: number;
  configName: string;
}

export type TimePeriod = {
  key: string;
  name: string;
}

export interface ValidationError {
  field: string;
  message: string;
}