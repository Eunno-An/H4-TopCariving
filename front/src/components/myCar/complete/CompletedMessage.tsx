import { Flex, Text } from '@components/common';
import styled from '@emotion/styled';
import { theme } from '@styles/theme';
import keyboardRight from '@assets/images/keyboardRight.svg';
import realSand from '@assets/images/realSand.png';
import palmTree from '@assets/images/palmTree.png';

export const CompletedMessage = ({ carColor }: { carColor: string }) => {
  return (
    <Flex height="auto" backgroundColor="LightSand" margin="32px 0 0 0">
      <MyCarComplete
        direction="column"
        align="flex-start"
        height={277}
        padding="76px 0 76px 110px"
        gap={17}
        width={1024}
      >
        <Flex direction="column" align="flex-start" gap={13}>
          <Text typo="Heading0_Medium">PALISADE</Text>
          <TitleBorder />
        </Flex>
        <Flex direction="column" align="flex-start" gap={3}>
          <Text typo="Heading1_Medium">나의 펠리세이드가 완성되었어요 !</Text>
          <Text typo="Caption_Regular">
            완성된 차량은 마이페이지 <img src={keyboardRight} alt="" />{' '}
            마이카이빙에서 볼 수 있어요
          </Text>
        </Flex>
        <PalmTreeImg src={palmTree} />
        <SandImg src={realSand} />
        <CarImg src={`/image/exterior/${carColor}/image_001.png`} alt="" />
      </MyCarComplete>
    </Flex>
  );
};

const MyCarComplete = styled(Flex)`
  position: relative;
`;

const TitleBorder = styled.div`
  width: 605px;
  border-bottom: 1px solid ${theme.palette.MediumGray};
`;

const CarImg = styled.img`
  position: absolute;

  width: 800px;
  height: auto;

  right: -115px;
  bottom: -98px;
  animation: testAni 1s cubic-bezier(0.455, 0.03, 0.515, 0.955);

  @keyframes testAni {
    from {
      right: -200px;
      opacity: 0;
    }
    to {
      right: -115px;
      opacity: 1;
    }
  }
`;

const SandImg = styled.img`
  position: absolute;
  width: 800px;

  right: -150px;
  bottom: -130px;

  animation: testAni2 0.7s cubic-bezier(0.455, 0.03, 0.515, 0.955);

  @keyframes testAni2 {
    from {
      right: -200px;
      opacity: 0;
    }
    to {
      right: -150px;
      opacity: 1;
    }
  }
`;

const PalmTreeImg = styled.img`
  position: absolute;
  width: 260px;

  right: -18px;
  bottom: 50px;

  animation: testAni1 0.9s cubic-bezier(0.455, 0.03, 0.515, 0.955);

  @keyframes testAni1 {
    from {
      right: -50px;
      opacity: 0;
    }
    to {
      right: -18px;
      opacity: 1;
    }
  }
`;
