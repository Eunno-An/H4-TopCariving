import styled from '@emotion/styled';
import { Button, Flex, Text } from '@components/common';
import { css } from '@emotion/react';
import { KeyOfPalette } from '@styles/theme';
import { AlertContextType, useAlert } from '@contexts/AlertContext';

export interface alertButtonInterface {
  text: string;
  color: KeyOfPalette;
  onClick: () => void;
}

interface alertModalInterface {
  content: string[];
  buttonInfo?: alertButtonInterface[];
}

export const AlertModal = ({ content, buttonInfo }: alertModalInterface) => {
  const { closeAlert } = useAlert() as AlertContextType;
  return (
    <Flex css={ModalContainerCss}>
      <div css={backCss} onClick={closeAlert}></div>
      <ModalContainer>
        <Flex direction="column" gap={6}>
          {content.map((text, idx) => (
            <Text key={`modalText_${idx}`} typo="Body1_Regular">
              {text}
            </Text>
          ))}
        </Flex>

        {buttonInfo && (
          <Flex gap={8}>
            {buttonInfo.map((btn, idx) => (
              <div
                onClick={btn.onClick}
                key={`modalButton_${idx}`}
                style={{ width: '50%' }}
              >
                <Button
                  backgroundColor={btn.color}
                  heightType="medium"
                  typo="Heading4_Medium"
                  width="100%"
                >
                  {btn.text}
                </Button>
              </div>
            ))}
          </Flex>
        )}
      </ModalContainer>
    </Flex>
  );
};

export const ModalContainerCss = () => css`
  position: absolute;
  top: 0;
  left: 0;
  bottom: 0;
  right: 0;
`;

export const backCss = () => css`
  position: fixed;
  top: 0;
  left: 0;
  bottom: 0;
  right: 0;
  background-color: rgba(0, 0, 0, 0.5);
  z-index: 10;
`;

const ModalContainer = styled.div`
  position: fixed;
  top: 409px;

  display: flex;
  flex-direction: column;
  align-items: center;

  padding: 30px 80px;
  gap: 20px;

  border-radius: 20px;
  background: #fff;

  /* 팝업창그림자 */
  box-shadow: 0px 12px 20px 0px rgba(0, 0, 0, 0.1);
  z-index: 11;
`;
