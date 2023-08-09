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
    <Flex css={containerCss}>
      <div css={backCss} onClick={closeAlert}></div>
      <Container>
        <Flex direction="column" gap={6}>
          {content.map((text, idx) => (
            <Text key={`modalText_${idx}`} typo="Body1_Medium">
              {text}
            </Text>
          ))}
        </Flex>

        {buttonInfo && (
          <Flex gap={8}>
            {buttonInfo.map((btn, idx) => (
              <div onClick={btn.onClick} key={`modalButton_${idx}`}>
                <Button
                  backgroundColor={btn.color}
                  width="auto"
                  heightType="medium"
                >
                  {btn.text}
                </Button>
              </div>
            ))}
          </Flex>
        )}
      </Container>
    </Flex>
  );
};

const containerCss = () => css`
  position: absolute;
  top: 0;
  left: 0;
  bottom: 0;
  right: 0;
`;

const backCss = () => css`
  position: absolute;
  top: 0;
  left: 0;
  bottom: 0;
  right: 0;
  background-color: rgba(0, 0, 0, 0.5);
`;

const Container = styled.div`
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
`;
