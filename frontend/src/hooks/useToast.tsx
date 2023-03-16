import {useContext} from "react";
import {ToastContext} from "../App";
import {ToastMessage} from "primereact/toast";

const useToast = () => {
  const toastContext = useContext(ToastContext);

  const toast = (message: ToastMessage) => {
    if(message.severity == undefined){
      message.severity = "success"
    }
    if(message.life == undefined){
      message.life = 2500;
    }
    // @ts-ignore
    toastContext.ref.current.show(message);
  }

  return toast;
};

export default useToast;