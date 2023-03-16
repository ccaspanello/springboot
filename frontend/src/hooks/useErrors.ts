import {ValidationError} from 'types';
import {useState} from 'react';

export const useErrors = () => {
  const [errors, setErrors] = useState<ValidationError[]>([]);

  function message(field: string): string | null {
    const fieldError: ValidationError | undefined = errors.find((error: ValidationError) => error.field === field);
    if (fieldError) {
      return fieldError.message;
    } else {
      return null;
    }
  }

  function has(field: string): boolean {
    return message(field) !== null;
  }

  function handle(err: any) {
    if (err.response !== undefined && err.response.status === 400) {
      setErrors(err.response.data);
    }
  }

  function clear() {
    setErrors([]);
  }

  return {
    clear: clear,
    handle: handle,
    has: has,
    message: message,
  };
};