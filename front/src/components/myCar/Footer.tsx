import { Button, Flex, Text } from '@components/common';
import styled from '@emotion/styled';
import { footerInterface } from '@pages/MyCar';

export const Footer = ({ footerInfo }: { footerInfo: footerInterface }) => {
  return (
    <Flex
      backgroundColor="Sand"
      height={108}
      borderRadius="16px 16px"
      padding="12px 0 24px 36px"
    >
      <Section width={138}>
        <Text typo="Body3_Regular" palette="DarkGray">
          트림
        </Text>
        <Text typo="Heading4_Bold" palette="Black">
          {footerInfo.name}
        </Text>
      </Section>
      <img src="/image/page/myCar/columnLine.svg" />
      <Section width={208}>
        <Text typo="Body3_Regular" palette="DarkGray">
          선택 색상
        </Text>
        <Flex justify="start" gap={12}>
          <Text typo="Body3_Regular" palette="Black">
            외장
          </Text>
          <Text></Text>
        </Flex>
        <Flex justify="start" gap={12}>
          <Text typo="Body3_Regular" palette="Black">
            내장
          </Text>
          <Text></Text>
        </Flex>
      </Section>
      <img src="/image/page/myCar/columnLine.svg" />
      <Section width={380}>
        <Text typo="Body3_Regular" palette="DarkGray">
          선택 옵션
        </Text>
      </Section>
      <img src="/image/page/myCar/columnLine.svg" />
      <Section width={380}>
        <Text typo="Body3_Regular" palette="DarkGray">
          예상 가격
        </Text>
        <Flex width="auto">
          <Text typo="Heading1_Bold" palette="Black">
            {`${footerInfo.price.toLocaleString('ko-KR')}`}
          </Text>
          <Text typo="Body3_Regular" palette="Black">
            원
          </Text>
        </Flex>
      </Section>
      <Button>다음 단계로</Button>
    </Flex>
  );
};

const Section = styled(Flex)`
  flex-direction: column;
  justify-content: flex-start;
  align-items: flex-start;

  gap: 6px;
  padding: 0 0 0 22px;
  box-sizing: border-box;
`;
