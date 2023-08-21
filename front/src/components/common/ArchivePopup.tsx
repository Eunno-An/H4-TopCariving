import styled from '@emotion/styled';
import { Flex, Text } from '.';
import popup from '@assets/images/popup.svg';
import { useEffect, useState } from 'react';

let popupTimer: number;

export const ArchivePopup = ({ desc }: { desc: string }) => {
  const [popupClose, setPopupClose] = useState(true);

  useEffect(() => {
    clearTimeout(popupTimer);
    popupTimer = setTimeout(() => {
      setPopupClose(false);
    }, 3000);
  }, []);

  return (
    <PopupContainer
      width={198}
      height={68}
      padding="10px 13px"
      popupClose={popupClose}
    >
      <PopupContent justify="center" align="flex-start" gap={10}>
        <PopupInfoText palette="White" typo="Body4_Regular">
          {desc}
        </PopupInfoText>
        <CloseButton palette="White" onClick={() => setPopupClose(true)}>
          x
        </CloseButton>
      </PopupContent>
      <PopupImg src={popup} />
    </PopupContainer>
  );
};

const CloseButton = styled(Text)`
  cursor: pointer;
`;

const PopupInfoText = styled(Text)`
  width: 156px;
`;

const PopupContainer = styled(Flex)<{ popupClose: boolean }>`
  display: ${({ popupClose }) => popupClose && 'none'};

  position: relative;
  bottom: 690px;
  left: 550px;
  z-index: 1;

  animation: popupOpen 0.5s ease-in-out;

  @keyframes popupOpen {
    0% {
      opacity: 0;
    }
    100% {
      opacity: 1;
    }
  }
`;

const PopupContent = styled(Flex)`
  position: absolute;
  top: 25px;
  right: 20px;
`;

const PopupImg = styled.img``;
