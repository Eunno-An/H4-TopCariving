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
    <PopupContainer>
      <PopupBox
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
      </PopupBox>
    </PopupContainer>
  );
};

const PopupContainer = styled.div`
  position: absolute;
  top: -55px;
  right: -20px;
`;

const CloseButton = styled(Text)`
  cursor: pointer;
`;

const PopupInfoText = styled(Text)`
  width: 156px;
`;

const PopupBox = styled(Flex)<{ popupClose: boolean }>`
  display: ${({ popupClose }) => popupClose && 'none'};

  position: relative;
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
  top: 17px;
`;

const PopupImg = styled.img``;
