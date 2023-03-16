import React, {ChangeEvent, useState} from 'react';
import {Dialog} from "primereact/dialog";
import {InputText} from "primereact/inputtext";
import {Button} from "primereact/button";
import axios from "axios";
import {ConfigValue, TimePeriod} from "types";
import useToast from "hooks/useToast";
import {useErrors} from "../../hooks/useErrors";

interface ConfigurationModalProps {
  timePeriod: TimePeriod;
  visible: boolean;
  onHide: () => void;
}

const ConfigurationModal = (props: ConfigurationModalProps) => {

  const toast = useToast();
  const errors = useErrors();

  const [configName, setConfigName] = useState<string>("");

  const doInputChange = (e: ChangeEvent<HTMLInputElement>) => {
    errors.clear();
    setConfigName(e.target.value);
  }

  const doCancel = () => {
    errors.clear();
    setConfigName("");
    props.onHide()
  }

  const doSave = () => {
    axios.put<ConfigValue>(`/configuration/${props.timePeriod.key}`, {configName: configName})
      .then(res => {
        const config = res.data;
        toast({
            summary: 'Added Configuration',
            detail: `${config.configId} : ${config.configName}`,
          }
        );
        setConfigName("");
        props.onHide()
      })
      .catch(err => {
        errors.handle(err)
      })

  }

  const footerContent = () => {
    return (
      <>
        <Button label="Cancel" icon="pi pi-times" severity="secondary" onClick={doCancel}/>
        <Button label="Save" icon="pi pi-save" severity="success" onClick={doSave}/>
      </>
    )
  }

  return (
    <>
      <Dialog header="Add Configuration" visible={props.visible} onHide={doCancel} closable={false}
              style={{width: '50vw'}} breakpoints={{'960px': '75vw', '641px': '100vw'}}
              footer={footerContent}>
        <div className="flex flex-column gap-2">
          <span>
             <label htmlFor="configName">Configuration Name</label>
            {errors.has('configName') &&
                <small id="username2-help" className="pl-4 p-error">Invalid: {errors.message('configName')}</small>}
          </span>
          <InputText id="configName" aria-describedby="configName-help" value={configName}
                     placeholder="Enter a name for your configuration."
                     onChange={doInputChange}/>
        </div>
      </Dialog>
    </>
  )
}

export default ConfigurationModal;