import styled from '@emotion/styled';
import {
  Button,
  Flex,
  Text,
  alertButtonInterface,
  backCss,
  containerCss,
} from '@components/common';
import { theme } from '@styles/theme';

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
  const windowHeight = screen.height;
  return (
    <Flex align="center" css={containerCss}>
      <div css={backCss} onClick={closeAlert}></div>
      <Container windowHeight={windowHeight}>
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

const Container = styled.div<{ windowHeight: number }>`
  width: 'auto';
  height: auto;
  position: fixed;
  top: ${({ windowHeight }) => windowHeight / 2};

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
