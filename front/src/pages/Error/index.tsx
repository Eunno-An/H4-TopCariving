import { alertButtonInterface } from '@components/common';
import { AlertContextType, useAlert } from '@contexts/AlertContext';
import { useEffect, useState } from 'react';

const Error = () => {
  const [isClose, setIsClose] = useState(0);
  const { isOpen, openAlert, closeAlert } = useAlert() as AlertContextType;

  useEffect(() => {
    openAlert({
      newContent: [
        '잘못된 URL입니다.',
        '이전 페이지로 돌아갑니다.',
      ] as string[],
      newButtonInfo: [
        {
          text: '확인',
          color: 'Primary',
          onClick: closeAlert,
        },
      ] as alertButtonInterface[],
    });
    setIsClose(isClose + 1);
  }, []);

  useEffect(() => {
    if (!isOpen && isClose !== 0) history.back();
  }, [isOpen]);

  return <></>;
};

export default Error;
