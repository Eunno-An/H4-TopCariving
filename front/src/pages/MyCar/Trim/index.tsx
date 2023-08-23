import { useEffect, useState } from 'react';
import { CarModel, Flex } from '@components/common';
import { TrimCard, TrimCardInterface } from '@components/myCar/trim';
import { MyCarContextType, useMyCar } from '@contexts/MyCarContext';
import { useLoaderData } from 'react-router-dom';

export const Trim = () => {
  const modelInfo = useLoaderData() as TrimCardInterface[];
  const { myCarInfo, setMyCarInfo } = useMyCar() as MyCarContextType;

  const [isSelected, setIsSelected] = useState(0);

  useEffect(() => {
    const getData = async () => {
      const localMyCarInfo = localStorage.getItem('myCarInfo');
      const localMyCar = localMyCarInfo
        ? JSON.parse(localMyCarInfo)
        : myCarInfo;

      if (modelInfo) {
        if (!localMyCar.trim.type) {
          const newMyCarInfo = {
            ...localMyCar,
            trim: {
              ...localMyCar.trim,
              type: {
                id: modelInfo[0].carOptionId,
                name: modelInfo[0].optionName,
                price: modelInfo[0].price,
              },
            },
            price: localMyCar.price + modelInfo[0].price,
          };
          setMyCarInfo(newMyCarInfo);
        } else {
          modelInfo.forEach((model, selectIdx) => {
            if (model.carOptionId === localMyCar.trim.type?.id) {
              setIsSelected(selectIdx);
            }
          });
          setMyCarInfo(localMyCar);
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
            price: modelInfo[idx].price,
          },
        },
        price:
          myCarInfo.price - modelInfo[isSelected].price + modelInfo[idx].price,
      });

    setIsSelected(idx);
  };

  return (
    <Flex direction="column" justify="flex-start" height="auto">
      <CarModel exteriorColor="abyss" />
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
