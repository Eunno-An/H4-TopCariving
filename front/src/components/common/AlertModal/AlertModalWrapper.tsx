import { AlertContextType, useAlert } from '@contexts/AlertContext';
import { AlertModal } from '..';

export const AlertModalWrapper = () => {
  const { isOpen, content, buttonInfo } = useAlert() as AlertContextType;

  return isOpen && <AlertModal content={content} buttonInfo={buttonInfo} />;
};
