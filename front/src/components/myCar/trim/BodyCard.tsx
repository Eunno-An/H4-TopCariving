import { Flex, Text } from '@components/common';
import styled from '@emotion/styled';
import { myCarOptionInterface } from '@interface/index';
import { theme } from '@styles/theme';

export const BodyCard = ({
  option,
  isSelected,
}: {
  option: myCarOptionInterface;
  isSelected: boolean;
}) => {
  return (
    <BodyBox
      direction="column"
      padding={'22px 16px'}
      gap={10}
      isSelected={isSelected === true}
    >
      <Flex justify="space-between">
        <Text typo="Heading3_Bold">{option.optionName}</Text>
        <Text typo="Heading4_Bold">
          +{option.price.toLocaleString('ko-KR')}원
        </Text>
      </Flex>
      <Flex>
        <Text typo="Body3_Regular">{option.optionDetail}</Text>
      </Flex>
    </BodyBox>
  );
};

const BodyBox = styled(Flex)<{ isSelected: boolean }>`
  width: 100%;
  max-width: 400px;
  background-color: ${({ isSelected }) =>
    isSelected ? 'rgba(0, 44, 95, 0.10)' : theme.palette.LightSand};
  border: ${({ isSelected }) =>
    isSelected ? `2px solid ${theme.palette.Primary}` : ''};
  color: ${({ isSelected }) =>
    isSelected ? theme.palette.Primary : theme.palette.Black};
  border-radius: 8px;
  cursor: pointer;
`;
