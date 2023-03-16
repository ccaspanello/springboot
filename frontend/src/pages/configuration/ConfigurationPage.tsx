import React, {useEffect, useRef, useState} from 'react';
import {DataTable} from "primereact/datatable";
import {Column} from "primereact/column";
import {ConfigValue, TimePeriod} from "types";
import axios from "axios";
import {Panel} from "primereact/panel";
import {Dropdown} from "primereact/dropdown";
import {Button} from "primereact/button";
import useToast from "hooks/useToast";
import ConfigurationModal from "./ConfigurationModal";

const ConfigurationPage = () => {

  // Could pull these from the backend but probably out of scope for coding assessment; down stream impact would be
  // to put crud operations on the time periods
  const timePeriods: TimePeriod[] = [
    {key: "012018", name: "Jan 2018"},
    {key: "022018", name: "Feb 2018"},
    {key: "032018", name: "Mar 2018"},
    {key: "042018", name: "Apr 2018"},
    {key: "052018", name: "May 2018"},
    {key: "062018", name: "Jun 2018"},
    {key: "072018", name: "Jul 2018"}
  ]

  const [selectedPeriod, setSelectedPeriod] = useState<TimePeriod>(timePeriods[0]);
  const [configValues, setConfigValues] = useState<ConfigValue[]>();
  const [modalVisible, setModalVisible] = useState<boolean>(false);

  const toast = useToast();

  useEffect(() => {
    fetchData();
  }, [selectedPeriod])

  const fetchData = () => {
    axios.get<ConfigValue[]>('/configuration/' + selectedPeriod.key)
      .then(res => setConfigValues(res.data));
  }

  const deleteAll = () => {
    axios.delete(`/configuration/${selectedPeriod.key}`)
      .then(res => {
        toast({
            summary: 'Deleted All Configurations',
          }
        );
        fetchData()
      });
  }

  const deleteOne = (configValue: ConfigValue) => {
    axios.delete(`/configuration/${selectedPeriod.key}/${configValue.configId}`)
      .then(res => {
        toast({
            summary: 'Deleted Configuration',
            detail: `${configValue.configId} : ${configValue.configName}`
          }
        );
        fetchData()
      });
  }

  const actionBody = (configValue: ConfigValue) => {
    return (<Button icon="pi pi-trash" size="small" severity="danger" tooltip="Delete"
                    onClick={() => deleteOne(configValue)}/>)
  }

  const header = () => {
    return (
      <div className="flex justify-content-between">
        <div>
          <Dropdown className="w-full md:w-14rem"
                    value={selectedPeriod} options={timePeriods} optionLabel="name"
                    onChange={(e) => setSelectedPeriod(e.value)}
                    placeholder="Select a Time Period"/>
        </div>
        <div>
          <Button label="Add" icon="pi pi-plus" className="p-button-sm mr-2" severity="success"
                  onClick={()=>setModalVisible(true)} />
          <Button label="Delete All" icon="pi pi-trash" className="p-button-sm" severity="danger"
                  disabled={configValues?.length == 0}
                  onClick={()=>deleteAll()} />
        </div>
      </div>
    );
  };

  const onHide = () => {
    setModalVisible(false);
    fetchData();
  }

  return (
    <Panel>
      <DataTable className="pt-2" value={configValues} tableStyle={{minWidth: '50rem'}} size="small"
                 paginator rows={15} rowsPerPageOptions={[15, 30, 60, 120]}
                 header={header}>
        <Column field="configId" header="Action" body={actionBody}/>
        <Column field="configId"
                sortable sortField="configId"
                filter filterField="configId"
                header="Configuration Id"/>
        <Column field="configName"
                sortable sortField="configName"
                filter filterField="configName"
                header="Configuration Value"/>
      </DataTable>
      <ConfigurationModal visible={modalVisible} onHide={onHide} timePeriod={selectedPeriod} />
    </Panel>
  )
}

export default ConfigurationPage;