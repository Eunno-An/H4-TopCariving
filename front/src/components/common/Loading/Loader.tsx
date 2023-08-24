import styled from '@emotion/styled';
import { Flex, Text } from '..';
import { theme } from '@styles/theme';
import car from '@assets/images/car.svg';

export const Loading = () => {
  return (
    <Flex justify="center" align="center">
      <LoadContainer>
        <ImgBox src={car} alt="carLoading" />
        <TextBox>페이지를 불러오고있어요</TextBox>
      </LoadContainer>
    </Flex>
  );
};

const ImgBox = styled.img`
  position: absolute;
`;

const TextBox = styled(Text)`
  position: absolute;
  top: 120px;

  white-space: nowrap;
`;

const LoadContainer = styled(Flex)`
  position: relative;

  width: 100px;
  height: 100px;

  border: 3px solid transparent;
  border-bottom: ${theme.palette.Primary};
  border-radius: 50%;

  animation: rotate 1s ease-in-out infinite;

  @keyframes rotate {
    0% {
    }

    25% {
      border-top: 3px solid ${theme.palette.Primary};
    }

    50% {
      border-right: 3px solid ${theme.palette.Primary};
    }

    75% {
      border-bottom: 3px solid ${theme.palette.Primary};
    }

    100% {
      border-left: 3px solid ${theme.palette.Primary};
    }

    75% {
      border-right: 3px solid ${theme.palette.Primary};
    }
  }
`;
