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

const Complete = () => {
  const { myCarInfo } = useMyCar();

  return (
    <Flex direction="column" justify="flex-start" gap={30}>
      <Header />
      <Flex
        direction="column"
        justify="flex-start"
        gap={30}
        margin="30px 0 0 0"
      >
        <CompletedMessage
          carColor={
            myCarInfo.color.exteriorColor
              ? colorPath[myCarInfo.color.exteriorColor.name]
              : 'black'
          }
        />
        <Flex
          width={1040}
          height={18}
          backgroundColor="LightSand"
          margin="83px 0 28px 0"
          borderRadius="8px"
        />
        <Information />
        <SelectOptionContainer />
        <Flex height={18} backgroundColor="LightSand" />
        <CompleteButton />
      </Flex>
      <Footer />
    </Flex>
  );
};

export default Complete;
