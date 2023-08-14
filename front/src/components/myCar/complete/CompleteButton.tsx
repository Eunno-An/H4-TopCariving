import { Button, Flex, Text } from '@components/common';
import styled from '@emotion/styled';
import keyboardRight from '@assets/images/keyboardRight.svg';
import { css } from '@emotion/react';

const progressInfo = [
  '구매상담 신청하기',
  'PDF 다운로드',
  '카탈로그 다운로드',
  '전시 차량 조회',
];

export const CompleteButton = () => {
  return (
    <Flex
      justify="space-between"
      align="flex-start"
      height={246}
      width={1280}
      padding="36px 0"
    >
      <Flex direction="column" height="auto" gap={28}>
        <Text typo="Heading3_Medium">
          딜러에게 가까운 출고일자 상담을 받아볼까요?
        </Text>
        <Flex
          width={355}
          height={52}
          justify="space-between"
          borderRadius="10px"
          padding="15px 24px"
          backgroundColor="LightSand"
          css={buttonCss}
        >
          <Text typo="Body3_Medium">출고일자 상담신청 바로가기</Text>
          <img src={keyboardRight} alt="" />
        </Flex>
      </Flex>
      <Flex direction="column" gap={28} align="flex-start">
        <Text typo="Heading3_Medium">
          구매를 위한 다른 절차가 필요하신가요?
        </Text>
        <Grid>
          {progressInfo.map((text, idx) => (
            <Button
              width={249}
              backgroundColor="LightSand"
              heightType="medium"
              typo="Body3_Medium"
              key={`button_${idx}`}
            >
              {text}
            </Button>
          ))}
        </Grid>
      </Flex>
    </Flex>
  );
};

const Grid = styled.div`
  display: grid;
  grid-template-rows: 1fr 1fr;
  grid-template-columns: 1fr 1fr;
  gap: 12px;
`;

const buttonCss = css`
  cursor: pointer;
`;
