import { Flex, Text } from '@components/common';
import { Tag } from '@components/common/Tag';
import styled from '@emotion/styled';
import { theme } from '@styles/theme';
import { OptionCard } from '@components/myCar/trim/OptionCard';
import { useState } from 'react';
import { OptionInfoCard } from '@components/myCar/trim/OptionInfoCard';
import { css } from '@emotion/react';
import vector478 from '@assets/images/Vector 478.svg';
import {
  OptionModal,
  alertContentInterface,
} from '@components/common/AlertModal/OptionModal';
import { useMyCar } from '@contexts/MyCarContext';
import { dummyOp, optionDummy } from './dummyOp';

const optionCategory = [
  '파워 트레인/성능',
  '지능형 안전 기술',
  '안전',
  '외관',
  '내장',
  '시트',
  '편의',
  '멀티미디어',
];

const defaultCategoryList = [
  'power',
  'intelligent',
  'safety',
  'exterior',
  'interior',
  'seats',
  'convenience',
  'multimedia',
];

const cateName = {
  select: '선택항목',
  default: '기본 포함 품목',
};

export const MyCarOptions = () => {
  const { myCarInfo, setMyCarInfo } = useMyCar();
  const [dummyData, setDummyData] = useState(optionDummy);

  const [selectedItem, setSelectedItem] = useState(0);
  const [selectedMenu, setSelectedMenu] = useState(cateName.select);
  const [defaultOption, setDefaultOption] = useState(0);

  const [cardPageIdx, setCarPageIdx] = useState(0);
  const [isOpen, setIsOpen] = useState(false);

  const [currentDefaultCategory, setCurrentDefaultCategory] =
    useState<string>('power');

  const [modalData, setModalData] = useState<alertContentInterface>({
    title: '',
    imgSrc: '',
    desc: '',
  });

  const getSelectedState = (optionIdx: number) => {
    for (const selectedOption of myCarInfo.selectedOption) {
      if (selectedOption.id === dummyData[optionIdx].carOptionId) {
        return true;
      }
    }
    return false;
  };

  const changeUserOptionList = (optionIdx: number) => {
    getSelectedState(optionIdx)
      ? setMyCarInfo({
          ...myCarInfo,
          price: myCarInfo.price - dummyData[optionIdx].price,
          selectedOption: myCarInfo.selectedOption.filter(
            (item) => item.id !== dummyData[optionIdx].carOptionId,
          ),
        })
      : setMyCarInfo({
          ...myCarInfo,
          price: myCarInfo.price + dummyData[optionIdx].price,
          selectedOption: [
            ...myCarInfo.selectedOption,
            {
              id: dummyData[optionIdx].carOptionId,
              name: dummyData[optionIdx].optionName,
            },
          ],
        });
  };

  const onMovePage = ({ pageParam }: { pageParam: number }) => {
    const info = dummyData[selectedItem].details;
    if (pageParam === -1) {
      if (cardPageIdx - 1 < 0) {
        setCarPageIdx(info.length - 1);
      } else {
        setCarPageIdx(cardPageIdx - 1);
      }
    } else {
      setCarPageIdx((cardPageIdx + 1) % info.length);
    }
  };

  const onSelectedItemHandler = (idx: number) => {
    setCarPageIdx(0);

    if (selectedMenu === cateName.select) {
      selectedMenu === cateName.select && setSelectedItem(idx);
    } else {
      onModalHandler(idx); // 기본 포함 품목일때 모달창 띄우기
    }
  };

  const onModalHandler = (idx: number) => {
    const modalOptionData: alertContentInterface = {
      title: dummyOp[currentDefaultCategory][idx].optionName,
      imgSrc: dummyOp[currentDefaultCategory][idx].photoUrl,
      desc: dummyOp[currentDefaultCategory][idx].optionDetail,
    };

    setIsOpen(true);
    setModalData({ ...modalOptionData });
  };

  const modalCloseHandler = () => {
    setIsOpen(false);
  };

  return (
    <Flex direction="column" justify="flex-start" height={561} gap={15}>
      {/* 옵션 상단 */}
      <Flex gap={39}>
        {/* 이미지 */}
        <Flex width={479} height={304}>
          <ImgContainer src={dummyData[selectedItem].photoUrl} alt="" />
        </Flex>
        {/* 옵션 Info */}
        <Flex direction="column">
          {/* 옵션 이름 / 가격 */}
          <OptionContainer>
            <Text typo="Heading1_Bold">
              {dummyData[selectedItem].optionName}
            </Text>
            <Text typo="Heading2_Bold">
              +{dummyData[selectedItem].price.toLocaleString('ko-KR')} 원
            </Text>
          </OptionContainer>
          {/* 옵션에대한 태그칩 */}
          <Flex
            width={507}
            height={108}
            padding="16px 0 0 0"
            direction="column"
            align="flex-start"
          >
            <Text>
              {dummyData[selectedItem].optionName}
              <Text typo="Body3_Regular">
                에 대해 시승자들은 이런 후기를 남겼어요
              </Text>
            </Text>
            <Flex gap={4} justify="flex-start" css={TagWrap}>
              <Tag desc="여름에 쓰기 좋아요☀️" />
              <Tag desc="옵션값 뽑았어요👍" />
              <Tag desc="편리해요☺️" />
            </Flex>
          </Flex>
          {/* 옵션 세부 설명 */}
          <OptionInfoCard
            info={dummyData[selectedItem].details}
            onMovePage={onMovePage}
            cardPageIdx={cardPageIdx}
          />
        </Flex>
      </Flex>

      {/* 옵션 하단 */}
      <Flex direction="column" height={245} gap={20}>
        {/* 선택 항목 / 기본 포함 항목 */}
        <OptionMenu justify="flex-start" height={40} gap={23}>
          <MenuName
            isSelected={selectedMenu === cateName.select}
            onClick={() => {
              if (selectedMenu !== cateName.select) {
                setCarPageIdx(0);
                setDummyData(optionDummy);
                setSelectedMenu(cateName.select);
              }
            }}
          >
            선택항목
          </MenuName>
          <MenuName
            isSelected={selectedMenu === cateName.default}
            onClick={() => {
              if (selectedMenu !== cateName.default) {
                setCarPageIdx(0);
                setSelectedMenu(cateName.default);
              }
            }}
          >
            기본 포함 품목
          </MenuName>
        </OptionMenu>
        {/* 기본 포함 품목 카테고리 */}
        {selectedMenu !== cateName.select && (
          <Flex justify="flex-start" gap={10} height={1}>
            {optionCategory.map((it, idx) => (
              <>
                <OptionTag
                  typo="Body4_Medium"
                  palette={defaultOption === idx ? 'Black' : 'MediumGray'}
                  onClick={() => {
                    setCurrentDefaultCategory(defaultCategoryList[idx]);
                    setDefaultOption(idx);
                  }}
                >
                  {it}
                </OptionTag>
                {idx !== 7 && <img src={vector478} alt="" />}
              </>
            ))}
          </Flex>
        )}
        {/* 옵션 카드 */}
        <Flex justify="flex-start" align="flex-end" gap={6} height={187}>
          {(selectedMenu === cateName.select
            ? dummyData
            : dummyOp[currentDefaultCategory]
          ).map((item, idx) => (
            <div
              key={`optionCard_${idx}`}
              onClick={() => onSelectedItemHandler(idx)}
            >
              <OptionCard
                idx={idx}
                isSelected={myCarInfo.selectedOption.some(
                  (item) => item.id === dummyData[idx].carOptionId,
                )}
                optionItem={item}
                changeUserOptionList={changeUserOptionList}
                selectedMenu={selectedMenu}
              />
            </div>
          ))}
        </Flex>
      </Flex>
      {isOpen && (
        <OptionModal
          content={modalData}
          buttonInfo={[
            {
              text: '확인',
              color: 'Primary',
              onClick: modalCloseHandler,
            },
          ]}
        />
      )}
    </Flex>
  );
};

const OptionTag = styled(Text)`
  cursor: pointer;
`;

const MenuName = styled(Text)<{ isSelected: boolean }>`
  ${({ isSelected }) =>
    isSelected ? theme.typo.Body1_Medium : theme.typo.Heading4_Bold};
  color: ${({ isSelected }) => !isSelected && theme.palette.LightGray};
  cursor: pointer;
`;

const TagWrap = css`
  flex-wrap: wrap;
`;

const OptionMenu = styled(Flex)`
  border-bottom: 3px solid ${theme.palette.LightGray};
`;

const OptionContainer = styled(Flex)`
  width: 507px;
  height: 44px;
  justify-content: space-between;

  border-bottom: 2px solid #545454;
`;

const ImgContainer = styled.img`
  width: 479px;
  height: 304px;
  border-radius: 4px;
`;
export interface selectOptionInterface {
  carOptionId: number;
  optionName: string;
  price: number;
  photoUrl: string;
  details: optionDetailInterface[];
  tags: optionTagInterface[];
}

interface optionDetailInterface {
  carOptionId: number;
  optionName: string;
  optionDetail: string;
}

export interface optionTagInterface {
  tagContent: string;
}

export interface optionItemInterface {
  carOptionId: number;
  optionName: string;
  optionDetail: string;
  price: number;
  photoUrl: string;
}

export interface defaultOptionInterface {
  [key: string]: optionItemInterface[];
}
