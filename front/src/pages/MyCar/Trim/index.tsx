import { Dispatch, SetStateAction, useState } from 'react';
import { useOutletContext } from 'react-router-dom';
import { myCarFooterInterface } from '@interface/index';
import { CarModel, Flex } from '@components/common';
import { TrimCard, TrimCardInterface } from '@components/myCar/trim';

const trimInfo = [
  {
    id: 1,
    name: 'Le Blanc',
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

export const Trim = () => {
  const { footerInfo, setFooterInfo } = useOutletContext<{
    footerInfo: myCarFooterInterface;
    setFooterInfo: Dispatch<SetStateAction<myCarFooterInterface>>;
  }>();

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
    setFooterInfo({
      ...footerInfo,
      name: [trimInfo[idx].name, footerInfo.name[1]],
      price: trimInfo[idx].price,
    });
  };

  return (
    <Flex direction="column" justify="flex-start" height="auto">
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
