import { Flex } from '@components/common';
import { Header } from '@components/myCar';
import {
  CompleteButton,
  CompletedMessage,
  Footer,
  Information,
  SelectOptionContainer,
} from '@components/myCar/complete';
import { colorPath } from '../Color';
import { useMyCar } from '@contexts/MyCarContext';
import styled from '@emotion/styled';
import { theme } from '@styles/theme';
import { css } from '@emotion/react';

const Complete = () => {
  const { myCarInfo } = useMyCar();

  return (
    <Flex direction="column">
      <Header />

      <Flex direction="column" justify="flex-start" gap={40}>
        <Flex
          height={50}
          css={css`
            flex-shrink: 0;
          `}
        />
        <CompletedMessage
          carColor={
            myCarInfo.color.exteriorColor
              ? colorPath[myCarInfo.color.exteriorColor.name]
              : 'black'
          }
        />
        <Information />
        <SelectOptionContainer />
        <RowLine />
        <CompleteButton />
        <Flex
          height={98}
          css={css`
            flex-shrink: 0;
          `}
        />
      </Flex>

      <Footer />
    </Flex>
  );
};

export default Complete;

const RowLine = styled(Flex)`
  width: 100%;
  height: 18px;
  background: ${theme.palette.LightSand};
  flex-shrink: 0;
`;
