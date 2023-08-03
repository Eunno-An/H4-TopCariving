import CarModel from '../../components/common/CarModel';
import { useState } from 'react';
import { Flex } from '../../components/common/Flex';
import TrimCard, { TrimCardInterface } from '../../components/myCar/TrimCard';

const Trim = () => {
  const [isSelectedArr, setIsSelectedArr] = useState([
    true,
    false,
    false,
    false,
  ] as boolean[]);

  const changeSelected = (idx: number) => {
    const newSelected = isSelectedArr.map((_, selectIdx) => {
      return selectIdx === idx;
    });
    setIsSelectedArr(newSelected);
  };

  return (
    <Flex direction="column" justify="space-between">
      <CarModel />
      <Flex gap={24}>
        {trimInfo.map((trim: TrimCardInterface, idx: number) => (
          <div onClick={() => changeSelected(idx)} key={`trimCard_${idx}`}>
            <TrimCard trim={trim} isSelected={isSelectedArr[idx]} />
          </div>
        ))}
      </Flex>
    </Flex>
  );
};

export default Trim;

const trimInfo = [
  {
    id: 1,
    name: 'Le Blanc(르블랑)',
    price: 47720000,
  },
  {
    id: 2,
    name: 'Exclusive',
    price: 40440000,
  },
  {
    id: 3,
    name: 'Prestige',
    price: 47720000,
  },
  {
    id: 4,
    name: 'Calligraphy',
    price: 52540000,
  },
] as TrimCardInterface[];
