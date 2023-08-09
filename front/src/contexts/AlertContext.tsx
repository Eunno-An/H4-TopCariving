import { alertButtonInterface } from '@components/common';
import { ReactNode, createContext, useContext, useState } from 'react';

interface openAlertProps {
  newContent: string[];
  newButtonInfo: alertButtonInterface[];
}
export interface AlertContextType {
  isOpen: boolean;
  content: string[];
  buttonInfo: alertButtonInterface[];
  openAlert: ({ newContent, newButtonInfo }: openAlertProps) => void;
  closeAlert: () => void;
}

const AlertContext = createContext<AlertContextType | null>(null);

export function useAlert() {
  const context = useContext(AlertContext);
  if (context === null) {
    throw new Error('useAlert must be used within an AlertProvider');
  }
  return context;
}

export function AlertProvider({ children }: { children: ReactNode }) {
  const [isOpen, setIsOpen] = useState(false);
  const [content, setContent] = useState<string[]>([]);
  const [buttonInfo, setButtonInfo] = useState<alertButtonInterface[]>([]);

  const openAlert = ({ newContent, newButtonInfo }: openAlertProps) => {
    setContent(newContent);
    setButtonInfo(newButtonInfo);
    setIsOpen(true);
  };

  const closeAlert = () => {
    console.log('closeAlert');
    setIsOpen(false);
  };

  const value = {
    isOpen,
    content,
    buttonInfo,
    openAlert,
    closeAlert,
  } as AlertContextType;

  return (
    <AlertContext.Provider value={value}>{children}</AlertContext.Provider>
  );
}
