import { CarModel, Flex, Text } from '@components/common';
import { ImgTag, InfoBox } from '../Trim/Engine';
import { Dispatch, SetStateAction, useState } from 'react';
import styled from '@emotion/styled';
import { theme } from '@styles/theme';
import check from '@assets/images/blueCheck.svg';
import { useOutletContext } from 'react-router-dom';
import { myCarFooterInterface } from '@interface/index';

interface colorResponseInterface {
  carOptionId: number;
  colorUrl: string;
  price: number;
  tagResponses: {
    tagContent: string;
  }[];
}
interface exteriorColorResponseInterface extends colorResponseInterface {
  optionName: colorEng;
}

interface interiorColorResponseInterface extends colorResponseInterface {
  optionName: string;
  photoUrl: string;
}

export type colorKey = 'exteriorColorResponses' | 'interiorColorResponses';

interface colorInfoInterface {
  exteriorColorResponses: exteriorColorResponseInterface[];
  interiorColorResponses: interiorColorResponseInterface[];
}

type colorEng =
  | '어비스블랙펄'
  | '쉬머링 실버 메탈릭'
  | '문라이프 블루 펄'
  | '가이아 브라운 펄'
  | '그라파이트 그레이 메탈릭'
  | '크리미 화이트 펄';

const colorPath = {
  어비스블랙펄: 'black',
  '쉬머링 실버 메탈릭': 'silver',
  '문라이프 블루 펄': 'blue',
  '가이아 브라운 펄': 'brown',
  '그라파이트 그레이 메탈릭': 'gray',
  '크리미 화이트 펄': 'white',
} as { [key in colorEng]: string };

const Color = () => {
  const { footerInfo, setFooterInfo } = useOutletContext<{
    footerInfo: myCarFooterInterface;
    setFooterInfo: Dispatch<SetStateAction<myCarFooterInterface>>;
  }>();

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  const [initialFooterPrice, _] = useState(footerInfo.price);

  const [selectedColorIdx, setSelectedColorIdx] = useState<{
    [key in colorKey]: number;
  }>({
    exteriorColorResponses: 0,
    interiorColorResponses: 0,
  });
  const [isLastClick, setIsLastClick] = useState<{
    key: keyof colorInfoInterface;
    idx: number;
  }>({
    key: 'exteriorColorResponses',
    idx: 0,
  });

  const onClickColorBox = (colorKey: colorKey, idx: number) => {
    const newSelectedColor = selectedColorIdx;
    selectedColorIdx[colorKey] = idx;
    setSelectedColorIdx(newSelectedColor);
    setIsLastClick({
      key: colorKey,
      idx: idx,
    });

    if (colorKey === 'exteriorColorResponses') {
      setModelColor(
        colorPath[colorInfo.exteriorColorResponses[idx].optionName],
      );
    }

    const newColorInfo = footerInfo.color;
    newColorInfo[colorKey] = colorInfo[colorKey][idx].optionName;

    setFooterInfo({
      ...footerInfo,
      color: newColorInfo,
      price: initialFooterPrice + colorInfo[colorKey][idx].price,
    });
  };

  const [modelColor, setModelColor] = useState('black');

  return (
    <Flex gap={28} padding="28px 0 0 0" align="flex-start">
      <Flex direction="column" gap={23} height="auto">
        <Flex width={620} height={397} align="center">
          {isLastClick.key === 'exteriorColorResponses' ? (
            <CarModel exteriorColor={modelColor} />
          ) : (
            <ImgTag
              src={colorInfo[isLastClick.key][isLastClick.idx].photoUrl}
              alt=""
            />
          )}
        </Flex>
        <Flex width={620} direction="column" justify="space-between">
          <InfoBox justify="space-between" align="flex-start" height={48}>
            <Text typo="Heading1_Bold">
              {colorInfo[isLastClick.key][isLastClick.idx].optionName}
            </Text>
            <Text typo="Heading2_Bold">
              {`+${colorInfo[isLastClick.key][
                isLastClick.idx
              ].price.toLocaleString('ko-KR')}원`}
            </Text>
          </InfoBox>
        </Flex>
      </Flex>

      <Flex direction="column" justify="flex-start">
        <Flex
          direction="column"
          justify="flex-start"
          gap={12}
          width={331}
          height="auto"
        >
          <Flex justify="space-between">
            <Text typo="Heading4_Medium">외장 색상</Text>
            <Text typo="Body4_Regular">
              {
                colorInfo.exteriorColorResponses[
                  selectedColorIdx.exteriorColorResponses
                ].optionName
              }
            </Text>
          </Flex>
          <Line />
          <GridContainer>
            {colorInfo.exteriorColorResponses.map((item, idx) => (
              <Flex direction="column" justify="flex-start" gap={8}>
                <ColorWrapper
                  isSelected={selectedColorIdx.exteriorColorResponses === idx}
                >
                  <ExteriorColor
                    src={item.colorUrl}
                    onClick={() => {
                      onClickColorBox('exteriorColorResponses', idx);
                    }}
                  />
                  <BlueCheck
                    src={check}
                    type={'exteriorColorResponses'}
                    isSelected={selectedColorIdx.exteriorColorResponses === idx}
                  />
                </ColorWrapper>

                <Text typo="Body4_Regular">{item.optionName}</Text>
              </Flex>
            ))}
          </GridContainer>
        </Flex>
        <Flex
          direction="column"
          justify="flex-start"
          gap={12}
          width={331}
          height="auto"
        >
          <Flex justify="space-between">
            <Text typo="Heading4_Medium">내장 색상</Text>
            <Text typo="Body4_Regular">
              {
                colorInfo.exteriorColorResponses[
                  selectedColorIdx.interiorColorResponses
                ].optionName
              }
            </Text>
          </Flex>
          <Line />
          <Flex direction="column" gap={16}>
            {colorInfo.interiorColorResponses.map((item, idx) => (
              <ColorWrapper
                key={`interiorCard_${idx}`}
                isSelected={selectedColorIdx.interiorColorResponses === idx}
              >
                <InteriorColor
                  src={item.colorUrl}
                  onClick={() => {
                    onClickColorBox('interiorColorResponses', idx);
                  }}
                />
                <BlueCheck
                  src={check}
                  type={'interiorColorResponses'}
                  isSelected={selectedColorIdx.interiorColorResponses === idx}
                />
              </ColorWrapper>
            ))}
          </Flex>
        </Flex>
      </Flex>
    </Flex>
  );
};

const GridContainer = styled.div`
  display: grid;
  grid-template-columns: repeat(3, 1fr); /* 3개의 열, 각 열은 동일한 너비 */
  grid-template-rows: repeat(2, 1fr); /* 2개의 행, 각 행은 200px 높이 */
  gap: 11px; /* 열과 행 사이의 간격 */
`;

const ColorWrapper = styled.div<{ isSelected: boolean }>`
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
  border: ${(props) =>
    props.isSelected
      ? `3px solid ${theme.palette.ActiveBlue}`
      : `3px solid ${theme.palette.White}`};

  border-radius: 8px;
  box-sizing: border-box;
`;

const ExteriorColor = styled.img`
  width: 89px;
  height: 89px;
  background: #f0f2f1;
  cursor: pointer;

  border-radius: 6px;
`;

const InteriorColor = styled.img`
  width: 100%;
  border-radius: 6px;
  cursor: pointer;
`;

const BlueCheck = styled.img<{ type: colorKey; isSelected: boolean }>`
  position: absolute;
  top: -11px;
  right: -11px;

  width: 22px;
  height: 22px;

  display: ${(props) => (props.isSelected ? 'block' : 'none')};
`;

const Line = styled.div`
  height: 2px;
  width: 100%;
  background-color: ${theme.palette.LightSand};
`;

const colorInfo = {
  exteriorColorResponses: [
    {
      carOptionId: 11,
      optionName: '어비스블랙펄',
      colorUrl:
        'https://topcariving.s3.ap-northeast-2.amazonaws.com/external_color/black.png',
      price: 0,
      tagResponses: [
        {
          tagContent: '태그칩 설명',
        },
      ],
    },
    {
      carOptionId: 11,
      optionName: '쉬머링 실버 메탈릭',
      colorUrl:
        'https://topcariving.s3.ap-northeast-2.amazonaws.com/external_color/silver.png',
      price: 0,
      tagResponses: [
        {
          tagContent: '태그칩 설명',
        },
      ],
    },
    {
      carOptionId: 11,
      optionName: '문라이프 블루 펄',
      colorUrl:
        'https://topcariving.s3.ap-northeast-2.amazonaws.com/external_color/blue.png',
      price: 0,
      tagResponses: [
        {
          tagContent: '태그칩 설명',
        },
      ],
    },
    {
      carOptionId: 11,
      optionName: '가이아 브라운 펄',
      colorUrl:
        'https://topcariving.s3.ap-northeast-2.amazonaws.com/external_color/brown.png',
      price: 0,
      tagResponses: [
        {
          tagContent: '태그칩 설명',
        },
      ],
    },
    {
      carOptionId: 11,
      optionName: '그라파이트 그레이 메탈릭',
      colorUrl:
        'https://topcariving.s3.ap-northeast-2.amazonaws.com/external_color/gray.png',
      price: 300000,
      tagResponses: [
        {
          tagContent: '태그칩 설명',
        },
      ],
    },
    {
      carOptionId: 11,
      optionName: '크리미 화이트 펄',
      colorUrl:
        'https://topcariving.s3.ap-northeast-2.amazonaws.com/external_color/white.png',
      price: 1200000,
      tagResponses: [
        {
          tagContent: 'string',
        },
      ],
    },
  ],
  interiorColorResponses: [
    {
      carOptionId: 11,
      optionName: '퀄팅천연(블랙)',
      photoUrl:
        'https://topcariving.s3.ap-northeast-2.amazonaws.com/internal_color/balck_internal.png',
      colorUrl:
        'https://topcariving.s3.ap-northeast-2.amazonaws.com/internal_color/black.png',
      price: 300000,
      tagResponses: [
        {
          tagContent: 'string',
        },
      ],
    },
    {
      carOptionId: 11,
      optionName: '쿨그레이',
      photoUrl:
        'https://topcariving.s3.ap-northeast-2.amazonaws.com/internal_color/gray_internal.png',
      colorUrl:
        'https://topcariving.s3.ap-northeast-2.amazonaws.com/internal_color/gray.png',
      price: 400000,
      tagResponses: [
        {
          tagContent: 'string',
        },
      ],
    },
  ],
} as colorInfoInterface;

export default Color;
