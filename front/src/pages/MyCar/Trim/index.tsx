import { Dispatch, SetStateAction, useEffect, useState } from 'react';
import { useOutletContext } from 'react-router-dom';
import { myCarFooterInterface } from '@interface/index';
import { CarModel, Flex } from '@components/common';
import { TrimCard, TrimCardInterface } from '@components/myCar/trim';
import { TrimUrl, apiInstance } from '@utils/api';

export const Trim = () => {
  const { footerInfo, setFooterInfo } = useOutletContext<{
    footerInfo: myCarFooterInterface;
    setFooterInfo: Dispatch<SetStateAction<myCarFooterInterface>>;
  }>();

  const [data, setData] = useState<TrimCardInterface[] | null>(null);

  useEffect(() => {
    const getData = async () => {
      const res = await apiInstance({
        url: TrimUrl.MODELS,
        method: 'GET',
      });
      setData(res);
    };
    getData();
  }, []);

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
    data &&
      setFooterInfo({
        ...footerInfo,
        name: [data[idx].optionName, footerInfo.name[1]],
        price: data[idx].price,
      });
  };

  return (
    <Flex direction="column" justify="flex-start" height="auto">
      <CarModel />
      <Flex gap={24}>
        {data && (
          <>
            {data.map((trim: TrimCardInterface, idx: number) => (
              <div onClick={() => changeSelected(idx)} key={`trimCard_${idx}`}>
                <TrimCard trim={trim} isSelected={isSelectedArr[idx]} />
              </div>
            ))}
          </>
        )}
      </Flex>
    </Flex>
  );
};
