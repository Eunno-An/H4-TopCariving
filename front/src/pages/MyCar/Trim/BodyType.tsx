import { Flex, Text } from '@components/common';
import { useEffect, useState } from 'react';
import { ImgTag, InfoBox } from './Engine';
import { myCarOptionInterface } from '@interface/index';
import { BodyCard } from '@components/myCar/trim';
import { useMyCar } from '@contexts/MyCarContext';
import { useLoaderData } from 'react-router-dom';

const BodyType = () => {
  const bodyTypeInfo = useLoaderData() as myCarOptionInterface[];
  const { myCarInfo, setMyCarInfo } = useMyCar();
  const [isSelected, setIsSelected] = useState(0);

  useEffect(() => {
    const getData = async () => {
      if (bodyTypeInfo) {
        if (!myCarInfo.trim.bodyType) {
          setMyCarInfo({
            ...myCarInfo,
            price: myCarInfo.price + bodyTypeInfo[0].price,
            trim: {
              ...myCarInfo.trim,
              bodyType: {
                id: bodyTypeInfo[0].carOptionId,
                name: bodyTypeInfo[0].optionName,
              },
            },
          });
        } else {
          bodyTypeInfo.forEach((bodyType, selectIdx) => {
            if (bodyType.carOptionId === myCarInfo.trim.bodyType?.id) {
              setIsSelected(selectIdx);
            }
          });
        }
      }
    };

    getData();
  }, []);

  const onSelectBodyType = (idx: number) => {
    bodyTypeInfo &&
      setMyCarInfo({
        ...myCarInfo,
        trim: {
          ...myCarInfo.trim,
          bodyType: {
            id: bodyTypeInfo[idx].carOptionId,
            name: bodyTypeInfo[idx].optionName,
          },
        },
        price:
          myCarInfo.price -
          bodyTypeInfo[isSelected].price +
          bodyTypeInfo[idx].price,
      });
    setIsSelected(idx);
  };

  return (
    <Flex padding="28px 0 0 0" align="flex-start" gap={28}>
      <Flex direction="column" height="auto" gap={23}>
        <Flex width={620} align="flex-start">
          <ImgTag src={bodyTypeInfo[isSelected].photoUrl} alt="" />
        </Flex>
        <Flex width={620} direction="column" justify="space-between">
          <InfoBox justify="space-between" align="flex-start" height={48}>
            <Text typo="Heading1_Bold">
              {bodyTypeInfo[isSelected].optionName}
            </Text>
            <Text typo="Heading2_Bold">
              +{bodyTypeInfo[isSelected].price.toLocaleString('ko-KR')}원
            </Text>
          </InfoBox>
        </Flex>
      </Flex>

      <Flex direction="column" justify="flex-start" gap={12}>
        {bodyTypeInfo.map((body, idx) => (
          <div
            key={`engineOption_${idx}`}
            onClick={() => {
              onSelectBodyType(idx);
            }}
          >
            <BodyCard option={body} isSelected={isSelected === idx} />
          </div>
        ))}
      </Flex>
    </Flex>
  );
};

export default BodyType;
