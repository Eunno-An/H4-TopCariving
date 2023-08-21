import { ReactNode, createContext, useContext, useState } from 'react';

export interface ToastContextType {
  isOpen: boolean;
  content: string;
  openToast: ({ newContent }: { newContent: string }) => void;
  closeToast: () => void;
}

const ToastContext = createContext<ToastContextType | null>(null);

export function useToast() {
  const context = useContext(ToastContext);
  if (context === null) {
    throw new Error('useToast must be used within an ToastProvider');
  }
  return context;
}

let toastTimeout: number;
export function ToastProvider({ children }: { children: ReactNode }) {
  const [isOpen, setIsOpen] = useState(false);
  const [content, setContent] = useState<string>();

  const openToast = ({ newContent }: { newContent: string }) => {
    setContent(newContent);
    setIsOpen(true);

    clearTimeout(toastTimeout);
    toastTimeout = setTimeout(() => {
      setIsOpen(false);
    }, 2000);
  };

  const closeToast = () => {
    setIsOpen(false);
  };

  const value = {
    isOpen,
    content,
    openToast,
    closeToast,
  } as ToastContextType;

  return (
    <ToastContext.Provider value={value}>{children}</ToastContext.Provider>
  );
}
