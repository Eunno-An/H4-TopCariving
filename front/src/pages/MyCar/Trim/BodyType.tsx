import { Flex, Text } from '@components/common';
import { Dispatch, SetStateAction, useState } from 'react';
import { ImgTag, InfoBox } from './Engine';
import { myCarFooterInterface, myCarOptionInterface } from '@interface/index';
import { useOutletContext } from 'react-router-dom';
import { BodyCard } from '@components/myCar/trim';

const bodyTypeInfo = [
  {
    carOptionId: 1,
    optionName: '7인승',
    optionDetail:
      '기존 8인승 시트(1열 2명, 2열 3명, 3열 3명)에서 2열 가운데 시트를 없애 2열 탑승객의 편의는 물론, 3열 탑승객의 승하차가 편리합니다.',
    price: 0,
    photoUrl:
      'https://topcariving.s3.ap-northeast-2.amazonaws.com/body_type/seven.jpg',
  },
  {
    carOptionId: 1,
    optionName: '8인승',
    optionDetail:
      '1열 2명, 2열 3명, 3열 3명이 탑승할 수 있는 구조로, 많은 인원이 탑승할 수 있도록 배려하였습니다',
    price: 0,
    photoUrl:
      'https://topcariving.s3.ap-northeast-2.amazonaws.com/body_type/eight.jpg',
  },
] as myCarOptionInterface[];

const BodyType = () => {
  const { footerInfo, setFooterInfo } = useOutletContext<{
    footerInfo: myCarFooterInterface;
    setFooterInfo: Dispatch<SetStateAction<myCarFooterInterface>>;
  }>();

  const [isSelected, setIsSelected] = useState(0);

  const onSelectBodyType = (idx: number) => {
    setIsSelected(idx);

    const newTrimOption = footerInfo.name[1].split('/');
    newTrimOption[1] = bodyTypeInfo[idx].optionName;

    setFooterInfo({
      ...footerInfo,
      name: [footerInfo.name[0], newTrimOption.join('/')],
    });
  };

  return (
    <Flex padding="28px 0 0 0" gap={28}>
      <Flex direction="column" gap={23}>
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
