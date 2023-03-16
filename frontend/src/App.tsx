import React, {RefObject, useRef} from 'react';
import Header from "./components/Header";
import ConfigurationPage from "./pages/configuration/ConfigurationPage";
import {Toast} from "primereact/toast";

interface ToastContextType {
  ref?: RefObject<Toast>;
}

const defaultToastContext: ToastContextType = {}

export const ToastContext = React.createContext(defaultToastContext)

function App() {

  const toast = useRef<Toast>(null);

  return (
    <>
      <Toast ref={toast} position={"top-center"}/>
      <ToastContext.Provider value={{ref: toast}}>
        <Header/>
        <ConfigurationPage/>
      </ToastContext.Provider>
    </>
  );
}

export default App;
