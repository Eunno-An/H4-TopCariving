import { Flex } from '@components/common';
import { Header } from '@components/myCar';
import {
  CompleteButton,
  CompletedMessage,
  Footer,
  Information,
  SelectOptionContainer,
} from '@components/myCar/complete';
import { colorEng } from '@interface/index';
import { useState } from 'react';
import { colorPath } from '../Color';
const Complete = () => {
  // type colorEng =
  // | '어비스블랙펄'
  // | '쉬머링 실버 메탈릭'
  // | '문라이프 블루 펄'
  // | '가이아 브라운 펄'
  // | '그라파이트 그레이 메탈릭'
  // | '크리미 화이트 펄';

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  const [footerInfo, _] = useState({
    name: ['Le Blanc', '디젤 2.2 / 4WD / 7인승'],
    color: {
      exteriorColorResponses: '크리미 화이트 펄' as colorEng,
      interiorColorResponses: '퀼팅 천연(블랙)',
    },
    option: [],
    price: 50720000,
  });

  return (
    <Flex direction="column" justify="flex-start" gap={30}>
      <Header />
      <CompletedMessage
        carColor={colorPath[footerInfo.color.exteriorColorResponses]}
      />
      <Flex
        width={1040}
        height={18}
        backgroundColor="LightSand"
        margin="83px 0 28px 0"
        borderRadius="8px"
      />
      <Information footerInfo={footerInfo} />
      <SelectOptionContainer />
      <Flex height={18} backgroundColor="LightSand" />
      <CompleteButton />
      <Footer footerInfo={footerInfo} />
    </Flex>
  );
};

export default Complete;
