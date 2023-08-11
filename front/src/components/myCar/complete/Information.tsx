import { Flex, Text } from '@components/common';
import { useMyCar } from '@contexts/MyCarContext';

export const Information = () => {
  const { myCarInfo } = useMyCar();

  return (
    <Flex direction="column" align="flex-start" width={1040} gap={16}>
      <Text typo="Heading2_Medium">
        나의 펠리세이드는 이런 기능을 가지고 있어요
      </Text>
      <Flex
        direction="column"
        height={230}
        backgroundColor="LightSand"
        padding="40px 65px"
        justify="space-between"
        align="flex-start"
        borderRadius="8px"
      >
        <Flex direction="column" gap={6} align="flex-start">
          <Text typo="Heading2_Medium">{`펠리세이드 ${myCarInfo.trim.type?.name}`}</Text>
          <Text typo="Body1_Regular">
            {Object.values(myCarInfo.trim)
              .map((trim, idx) =>
                idx !== 0 && trim !== null ? trim.name : null,
              )
              .filter((name) => name !== null)
              .join('/')}
          </Text>
        </Flex>
        <Flex height={3} backgroundColor="Sand" margin="10px 0 40px 0"></Flex>
        <Flex gap={49} width="auto" height="auto">
          <Flex justify="flex-start" width="auto" height="auto">
            <Text typo="Heading2_Medium">외장</Text>
            <Flex
              backgroundColor="Primary"
              borderRadius="100px"
              width={24}
              height={24}
              margin="0 24px 0 16px"
            />
            <Text typo="Body1_Regular">
              {myCarInfo.color.exteriorColor?.name}
            </Text>
          </Flex>
          <Flex justify="flex-start" width="auto" height="auto">
            <Text typo="Heading2_Medium">내장</Text>
            <Flex
              backgroundColor="Primary"
              borderRadius="100px"
              width={24}
              height={24}
              margin="0 24px 0 16px"
            />
            <Text typo="Body1_Regular">
              {myCarInfo.color.interiorColor?.name}
            </Text>
          </Flex>
        </Flex>
      </Flex>
    </Flex>
  );
};
