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
      if (modelInfo) {
        if (!myCarInfo.trim.type) {
          setMyCarInfo({
            ...myCarInfo,
            trim: {
              ...myCarInfo.trim,
              type: {
                id: modelInfo[0].carOptionId,
                name: modelInfo[0].optionName,
                price: modelInfo[0].price,
              },
            },
            price: myCarInfo.price + modelInfo[0].price,
          });
        } else {
          modelInfo.forEach((model, selectIdx) => {
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
