import { Flex, Text } from '@components/common';
import { useMyCar } from '@contexts/MyCarContext';
import styled from '@emotion/styled';
import { theme } from '@styles/theme';

export const Information = () => {
  const { myCarInfo } = useMyCar();

  return (
    <Flex direction="column" align="flex-start" width={1040} gap={16}>
      <Text typo="Heading2_Medium">
        나의 펠리세이드는 이런 기능을 가지고 있어요
      </Text>
      <Flex
        direction="column"
        backgroundColor="LightSand"
        padding="40px 65px"
        justify="space-between"
        align="flex-start"
        borderRadius="8px"
      >
        <Flex direction="column" gap={6} align="flex-start" height="auto">
          <Text typo="Heading2_Medium">{`펠리세이드 ${myCarInfo.trim.type?.name}`}</Text>
          <Flex justify="space-between">
            <Text typo="Body1_Regular">
              {Object.values(myCarInfo.trim)
                .map((trim, idx) =>
                  idx !== 0 && trim !== null ? trim.name : null,
                )
                .filter((name) => name !== null)
                .join('/')}
            </Text>
            <Text typo="Heading2_Medium">{`${myCarInfo.price.toLocaleString()}원`}</Text>
          </Flex>
        </Flex>
        <RowLine />
        <Flex gap={49} width="auto" height="auto">
          <Flex justify="flex-start" width="auto" height="auto">
            <Text typo="Heading2_Medium">외장</Text>
            <ColorCircle src={myCarInfo.color.exteriorColor?.url} />
            <Text typo="Body1_Regular">
              {myCarInfo.color.exteriorColor?.name}
            </Text>
          </Flex>
          <Flex justify="flex-start" width="auto" height="auto">
            <Text typo="Heading2_Medium">내장</Text>
            <ColorCircle src={myCarInfo.color.interiorColor?.url} />
            <Text typo="Body1_Regular">
              {myCarInfo.color.interiorColor?.name}
            </Text>
          </Flex>
        </Flex>
      </Flex>
    </Flex>
  );
};

const RowLine = styled.div`
  width: 100%;
  height: 3px;
  flex-shrink: 0;
  background-color: ${theme.palette.Sand};
  margin: 17px 0 47px 0;
`;

const ColorCircle = styled.img`
  border-radius: 100px;
  width: 24px;
  height: 24px;
  margin: 0 24px 0 16px;
`;
