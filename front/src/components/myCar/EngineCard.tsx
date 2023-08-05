import styled from '@emotion/styled';
import { Flex } from '../common/Flex';
import { theme } from '../../styles/theme';
import { Text } from '../common/Text';
import { engineOptionInterface } from '../../pages/MyCar/Engine';

const EngineCard = ({
  engine,
  isSelected,
}: {
  engine: engineOptionInterface;
  isSelected: boolean;
}) => {
  return (
    <EngineBox
      direction="column"
      padding={isSelected ? '0px 20.8px' : '2px 22.8px'}
      height={engine.height}
      isSelected={isSelected === true}
    >
      <Flex justify="space-between">
        <Text typo="Heading3_Bold">{engine.engineType}</Text>
        <Text typo="Heading4_Bold">
          +{engine.price.toLocaleString('ko-KR')}원
        </Text>
      </Flex>
      <Flex>
        <Text typo="Body3_Regular">{engine.description}</Text>
      </Flex>
      <BorderLine />
      <Flex direction="column" gap={8}>
        <Flex justify="space-between" height={18}>
          <Text typo="Body3_Medium">최고출력</Text>
          <Text typo="Body3_Regular">{engine.maximumPower}</Text>
        </Flex>
        <Flex justify="space-between" height={18}>
          <Text typo="Body3_Medium">최대토크</Text>
          <Text typo="Body3_Regular">{engine.maximumTorque}</Text>
        </Flex>
      </Flex>
    </EngineBox>
  );
};

const EngineBox = styled(Flex)<{ isSelected: boolean }>`
  width: 391px;
  background-color: ${({ isSelected }) =>
    isSelected ? 'rgba(0, 44, 95, 0.10)' : theme.palette.LightSand};
  border: ${({ isSelected }) =>
    isSelected ? `2px solid ${theme.palette.Primary}` : ''};
  color: ${({ isSelected }) =>
    isSelected ? theme.palette.Primary : theme.palette.Black};
  border-radius: 8px;
  cursor: pointer;
`;

const BorderLine = styled.div`
  width: 345.402px;
  height: 0px;
  flex-shrink: 0;

  border: 1px solid ${theme.palette.Primary};
`;

export default EngineCard;
