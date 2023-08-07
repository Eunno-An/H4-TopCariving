import { Flex, Text, Btn } from '@components/common';
import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { theme } from '@styles/theme';
import check from '@assets/images/check.svg';

export const OptionCard = ({
  isSelected,
  idx,
}: {
  isSelected: number;
  idx: number;
}) => {
  return (
    <Card direction="column">
      <Flex width={160} height={93}>
        <ImgContainer src="https://topcariving.s3.ap-northeast-2.amazonaws.com/ai/hba.jpeg" />
      </Flex>
      <Flex direction="column" align="flex-start" padding="20px 9px" gap={8}>
        <Text>컴포트 2</Text>
        <Text>+ 1,090,000 원</Text>
        <Btn
          backgroundColor="White"
          heightType="small"
          width={142}
          css={isSelected === idx ? selected : notSelected}
          border={4}
        >
          {isSelected === idx ? (
            <Flex gap={2}>
              <Text typo="Body3_Medium">추가완료</Text>
              <img src={check} alt="" />
            </Flex>
          ) : (
            <Text typo="Body3_Medium">추가하기</Text>
          )}
        </Btn>
      </Flex>
    </Card>
  );
};

const selected = css`
  color: ${theme.palette.White};
  background-color: #385da2;
`;
const notSelected = css`
  color: #385da2;
  border: 2px solid #385da2;
`;

const Card = styled(Flex)`
  width: 160px;
  height: 197px;
  border-radius: 8px;
  background-color: ${theme.palette.LightSand};
`;

const ImgContainer = styled.img`
  width: 160px;
  height: 93px;

  border-top-left-radius: 8px;
  border-top-right-radius: 8px;
`;
