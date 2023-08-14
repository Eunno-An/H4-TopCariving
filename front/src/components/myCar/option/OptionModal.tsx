import styled from '@emotion/styled';
import {
  Button,
  Flex,
  ModalContainerCss,
  Text,
  alertButtonInterface,
  backCss,
} from '@components/common';
import { theme } from '@styles/theme';
import { CloseIcon } from '.';
import close from '@assets/images/close.svg';

export interface alertContentInterface {
  title: string;
  imgSrc: string;
  desc: string;
}

interface alertModalInterface {
  content: alertContentInterface;
  buttonInfo: alertButtonInterface[];
}

export const OptionModal = ({ content, buttonInfo }: alertModalInterface) => {
  const closeAlert = buttonInfo[0].onClick;

  return (
    <Flex align="center" css={ModalContainerCss}>
      <div css={backCss} onClick={closeAlert}></div>

      <Container>
        <CloseIcon src={close} onClick={closeAlert} />
        <Flex direction="column" height="auto">
          <Text key={`modalText_${content.title}`} typo="Heading4_Medium">
            {content.title}
          </Text>
        </Flex>
        <Border />
        <Flex align="center" justify="center" height="auto">
          <Flex direction="column" align="flex-start" width={668} gap={24}>
            <ImgContainer src={content.imgSrc} alt="" />
            <Text typo="Body4_Regular" palette="Black">
              {content.desc}
            </Text>
          </Flex>
        </Flex>

        {buttonInfo && (
          <Flex height="auto">
            {buttonInfo.map((btn, idx) => (
              <div onClick={btn.onClick} key={`modalButton_${idx}`}>
                <Button
                  backgroundColor={btn.color}
                  width={176}
                  heightType="medium"
                  typo="Heading4_Bold"
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

const Border = styled.div`
  width: 100%;
  border-top: 1px solid ${theme.palette.LightGray};
`;

const ImgContainer = styled.img`
  width: 668px;
  height: 357px;

  border-radius: 8px;
`;

const Container = styled.div`
  position: fixed;

  display: flex;
  flex-direction: column;
  align-items: center;

  width: auto;
  height: auto;

  padding: 30px 80px;
  gap: 20px;

  border-radius: 20px;
  background: #fff;

  /* 팝업창그림자 */
  box-shadow: 0px 12px 20px 0px rgba(0, 0, 0, 0.1);
`;
