import { useEffect, useState } from 'react';
import { CarModel, Flex } from '@components/common';
import { TrimCard, TrimCardInterface } from '@components/myCar/trim';
import { TrimUrl, apiInstance } from '@utils/api';
import { MyCarContextType, useMyCar } from '@contexts/MyCarContext';

export const Trim = () => {
  const { myCarInfo, setMyCarInfo } = useMyCar() as MyCarContextType;
  const [modelInfo, setModelInfo] = useState<TrimCardInterface[] | null>(null);

  const [isSelected, setIsSelected] = useState(0);

  useEffect(() => {
    const getData = async () => {
      const res = (await apiInstance({
        url: TrimUrl.MODELS,
        method: 'GET',
      })) as TrimCardInterface[];
      setModelInfo(res);

      if (res) {
        if (myCarInfo.trim.type === null) {
          setMyCarInfo({
            ...myCarInfo,
            trim: {
              ...myCarInfo.trim,
              type: {
                id: res[0].carOptionId,
                name: res[0].optionName,
              },
            },
            price: myCarInfo.price + res[0].price,
          });
        } else {
          res.forEach((model, selectIdx) => {
            if (model.carOptionId === myCarInfo.trim.type?.id) {
              setIsSelected(selectIdx);
            }
          });
        }
      }
    };
    getData();
  }, []);

  const changeSelected = (idx: number) => {
    modelInfo &&
      setMyCarInfo({
        ...myCarInfo,
        trim: {
          ...myCarInfo.trim,
          type: {
            name: modelInfo[idx].optionName,
            id: modelInfo[idx].carOptionId,
          },
        },
        price:
          myCarInfo.price - modelInfo[isSelected].price + modelInfo[idx].price,
      });

    setIsSelected(idx);
  };

  return (
    <Flex direction="column" justify="flex-start" height="auto">
      <CarModel exteriorColor="black" />
      <Flex gap={24}>
        {modelInfo && (
          <>
            {modelInfo.map((trim: TrimCardInterface, idx: number) => (
              <div onClick={() => changeSelected(idx)} key={`trimCard_${idx}`}>
                <TrimCard trim={trim} isSelected={isSelected === idx} />
              </div>
            ))}
          </>
        )}
      </Flex>
    </Flex>
  );
};
