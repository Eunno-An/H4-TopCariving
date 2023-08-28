import { Button, Flex, Text } from '@components/common';
import { useMyCar } from '@contexts/MyCarContext';
import { useToast } from '@contexts/ToastContext';
import { css } from '@emotion/react';

export const Footer = () => {
  const { myCarInfo } = useMyCar();
  const { openToast } = useToast();
  const noActionToast = () => {
    openToast({ newContent: '현재는 지원하지 않는 기능이에요.' });
  };
  return (
    <Flex
      backgroundColor="Sand"
      height={108}
      borderRadius="16px 16px"
      padding="12px 36px 24px 36px"
      justify="flex-end"
      gap={24}
      css={css`
        position: fixed;
        right: 0;
        bottom: 0;
      `}
    >
      <Flex width="auto" gap={4} margin="18px 0 0 0">
        <Text typo="Body3_Medium" palette="DarkGray">
          예상 견적 가격
        </Text>
        <Text typo="Heading1_Bold">{myCarInfo.price.toLocaleString()}</Text>
        <Text typo="Body3_Medium">원</Text>
      </Flex>
      <Button
        backgroundColor="Primary"
        typo="Heading4_Bold"
        heightType="medium"
        width={176}
        onClick={noActionToast}
      >
        이 차량 구매하기
      </Button>
    </Flex>
  );
};
