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
import { useLoaderData } from 'react-router-dom';
import { ErrorBoundary } from '@components/ErrorBoundary';

const Complete = () => {
  const { myCarInfo } = useMyCar();
  const options = useLoaderData() as completeOptionInterface;

  return (
    <Flex direction="column">
      <Header />

      <ErrorBoundary>
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
          <SelectOptionContainer options={options.선택품목} />
          <RowLine />
          <CompleteButton />
          <Flex
            height={98}
            css={css`
              flex-shrink: 0;
            `}
          />
        </Flex>
      </ErrorBoundary>

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

export type completeOptionKeyType = '모델' | '색상' | '선택품목' | '트림';
export interface completeOptionValue {
  carOptionId: number;
  category: string;
  categoryDetail: string;
  name: string;
  photoUrl: string;
  price: number;
}

export interface completeOptionInterface {
  모델: completeOptionValue[];
  색상: completeOptionValue[];
  선택품목: completeOptionValue[];
  트림: completeOptionValue[];
}
