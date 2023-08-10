import { Flex, Text } from '@components/common';
import { Tag } from '@components/common/Tag';
import styled from '@emotion/styled';
import { theme } from '@styles/theme';
import { OptionCard } from '@components/myCar/trim/OptionCard';
import { useEffect, useState } from 'react';
import { OptionInfoCard } from '@components/myCar/trim/OptionInfoCard';
import { css } from '@emotion/react';
import vector478 from '@assets/images/Vector 478.svg';
import {
  OptionModal,
  alertContentInterface,
} from '@components/common/AlertModal/OptionModal';

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
  const [userOptionList, setUserOptionList] = useState<string[]>([]);
  const [selectedMenu, setSelectedMenu] = useState(cateName.select);
  const [defaultOption, setDefaultOption] = useState(0);
  const [dummyData, setDummyData] = useState(optionDummy);
  const [selectedItem, setSelectedItem] = useState(0);
  const [defaultSelectedItem, setDefaultSelectedItem] = useState(0);
  const [currentIdx, setCurrentIdx] = useState(0);
  const [isOpen, setIsOpen] = useState(false);
  const [currnetDefaultCategory, setCurrentDefaultCategory] =
    useState<string>('power');
  const [modalData, setModalData] = useState<alertContentInterface>({
    title: '',
    imgSrc: '',
    desc: '',
  });

  const userOptionHandler = (option: string, actionType: string) => {
    actionType === 'ADD' ? userAddOptions(option) : userDeleteOptions(option);
  };

  const userAddOptions = (option: string) => {
    setUserOptionList((it) => [...it, option]);
  };
  const userDeleteOptions = (option: string) => {
    const newData = userOptionList.filter((it) => it !== option);
    setUserOptionList([...newData]);
  };

  const onPageBtnClickHandler = (btnType: string) => {
    btnType === 'left' ? onLeftPage() : onRightPage();
  };

  const onLeftPage = () => {
    const info = dummyData[selectedItem].details;
    currentIdx - 1 < 0
      ? setCurrentIdx(info.length - 1)
      : setCurrentIdx(currentIdx - 1);
  };

  const onRightPage = () => {
    const info = dummyData[selectedItem].details;
    setCurrentIdx((currentIdx + 1) % info.length);
  };

  const onSelectedItemHandler = (idx: number) => {
    setCurrentIdx(0);

    if (selectedMenu === cateName.select) {
      selectedMenu === cateName.select && setSelectedItem(idx);
    } else {
      setDefaultSelectedItem(idx);
      onModalHandler(idx); // 기본 포함 품목일때 모달창 띄우기
    }
  };

  const onModalHandler = (idx: number) => {
    const modalOptionData: alertContentInterface = {
      title: dummyOp[currnetDefaultCategory][idx].optionName,
      imgSrc: dummyOp[currnetDefaultCategory][idx].photoUrl,
      desc: dummyOp[currnetDefaultCategory][idx].optionDetail,
    };

    setIsOpen(true);
    setModalData({ ...modalOptionData });
  };

  const modalCloseHandler = () => {
    setIsOpen(false);
  };

  useEffect(() => {}, [userOptionList]);

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
            onPageBtnClickHandler={onPageBtnClickHandler}
            currentIdx={currentIdx}
          />
        </Flex>
      </Flex>

      {/* 옵션 하단 */}
      <Flex direction="column" height={245} gap={20}>
        {/* 선택 항목 / 기본 포함 항목 */}
        <OptionMenu justify="flex-start" height={40} gap={23}>
          {selectedMenu === cateName.select ? (
            <>
              <FocusMenu>선택항목</FocusMenu>
              <NonFocusMenu
                onClick={() => {
                  setCurrentIdx(0);
                  // setSelectedItem(0);
                  setSelectedMenu(cateName.default);
                }}
              >
                기본 포함 품목
              </NonFocusMenu>
            </>
          ) : (
            <>
              <NonFocusMenu
                onClick={() => {
                  setCurrentIdx(0);
                  // setSelectedItem(0);
                  setDummyData(optionDummy);
                  setSelectedMenu(cateName.select);
                }}
              >
                선택항목
              </NonFocusMenu>
              <FocusMenu>기본 포함 품목</FocusMenu>
            </>
          )}
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
                    setDefaultSelectedItem(0);
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
            : dummyOp[currnetDefaultCategory]
          ).map((item, idx) => (
            <div
              key={`optionCard_${idx}`}
              onClick={() => onSelectedItemHandler(idx)}
            >
              <OptionCard
                idx={idx}
                isSelected={
                  selectedMenu === cateName.select
                    ? selectedItem === idx
                    : defaultSelectedItem === idx
                }
                optionName={item.optionName}
                userOptionList={userOptionList}
                price={item.price}
                photo={item.photoUrl}
                userOptionHandler={userOptionHandler}
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

const FocusMenu = styled(Text)`
  ${theme.typo.Body1_Medium};
  cursor: pointer;
`;

const NonFocusMenu = styled(Text)`
  ${theme.typo.Heading4_Bold};
  color: ${theme.palette.LightGray};
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
interface selectOptionInterface {
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

interface optionTagInterface {
  tagContent: string;
}
interface defaultOptionInterface {
  [key: string]: {
    carOptionId: number;
    optionName: string;
    optionDetail: string;
    price: number;
    photoUrl: string;
  }[];
}
const dummyOp: defaultOptionInterface = {
  power: [
    {
      carOptionId: 1,
      optionName: '파워트레인 1',
      optionDetail: '높은 토크로 파워풀한 드라이빙이 가능합니다',
      price: 1480000,
      photoUrl:
        'https://topcariving.s3.ap-northeast-2.amazonaws.com/ai/daw.jpeg',
    },
    {
      carOptionId: 2,
      optionName: '파워트레인 2',
      optionDetail: '높은 토크로 파워풀한 드라이빙이 가능합니다',
      price: 1480000,
      photoUrl:
        'https://topcariving.s3.ap-northeast-2.amazonaws.com/ai/daw.jpeg',
    },
    {
      carOptionId: 3,
      optionName: '파워트레인 3',
      optionDetail: '높은 토크로 파워풀한 드라이빙이 가능합니다',
      price: 1480000,
      photoUrl:
        'https://topcariving.s3.ap-northeast-2.amazonaws.com/ai/daw.jpeg',
    },
  ],
  intelligent: [
    {
      carOptionId: 1,
      optionName: '지능형시스템 1',
      optionDetail: '높은 토크로 파워풀한 드라이빙이 가능합니다',
      price: 1480000,
      photoUrl:
        'https://topcariving.s3.ap-northeast-2.amazonaws.com/ai/daw.jpeg',
    },
    {
      carOptionId: 1,
      optionName: '지능형시스템 2',
      optionDetail: '높은 토크로 파워풀한 드라이빙이 가능합니다',
      price: 1480000,
      photoUrl:
        'https://topcariving.s3.ap-northeast-2.amazonaws.com/ai/daw.jpeg',
    },
  ],
  safety: [
    {
      carOptionId: 1,
      optionName: '안전 1',
      optionDetail: '높은 토크로 파워풀한 드라이빙이 가능합니다',
      price: 1480000,
      photoUrl:
        'https://topcariving.s3.ap-northeast-2.amazonaws.com/ai/daw.jpeg',
    },
  ],
  exterior: [
    {
      carOptionId: 1,
      optionName: '안전 1',
      optionDetail: '높은 토크로 파워풀한 드라이빙이 가능합니다',
      price: 1480000,
      photoUrl:
        'https://topcariving.s3.ap-northeast-2.amazonaws.com/ai/daw.jpeg',
    },
    {
      carOptionId: 2,
      optionName: '안전 2',
      optionDetail: '높은 토크로 파워풀한 드라이빙이 가능합니다',
      price: 1480000,
      photoUrl:
        'https://topcariving.s3.ap-northeast-2.amazonaws.com/ai/daw.jpeg',
    },
    {
      carOptionId: 3,
      optionName: '안전 3',
      optionDetail: '높은 토크로 파워풀한 드라이빙이 가능합니다',
      price: 1480000,
      photoUrl:
        'https://topcariving.s3.ap-northeast-2.amazonaws.com/ai/daw.jpeg',
    },
    {
      carOptionId: 4,
      optionName: '안전 4',
      optionDetail: '높은 토크로 파워풀한 드라이빙이 가능합니다',
      price: 1480000,
      photoUrl:
        'https://topcariving.s3.ap-northeast-2.amazonaws.com/ai/daw.jpeg',
    },
  ],
  interior: [
    {
      carOptionId: 1,
      optionName: '내장 1',
      optionDetail: '높은 토크로 파워풀한 드라이빙이 가능합니다',
      price: 1480000,
      photoUrl:
        'https://topcariving.s3.ap-northeast-2.amazonaws.com/ai/daw.jpeg',
    },
  ],
  seats: [
    {
      carOptionId: 1,
      optionName: '시트 1',
      optionDetail: '높은 토크로 파워풀한 드라이빙이 가능합니다',
      price: 1480000,
      photoUrl:
        'https://topcariving.s3.ap-northeast-2.amazonaws.com/ai/daw.jpeg',
    },
    {
      carOptionId: 2,
      optionName: '시트 2',
      optionDetail: '높은 토크로 파워풀한 드라이빙이 가능합니다',
      price: 1480000,
      photoUrl:
        'https://topcariving.s3.ap-northeast-2.amazonaws.com/ai/daw.jpeg',
    },
    {
      carOptionId: 3,
      optionName: '시트 3',
      optionDetail: '높은 토크로 파워풀한 드라이빙이 가능합니다',
      price: 1480000,
      photoUrl:
        'https://topcariving.s3.ap-northeast-2.amazonaws.com/ai/daw.jpeg',
    },
    {
      carOptionId: 4,
      optionName: '시트 4',
      optionDetail: '높은 토크로 파워풀한 드라이빙이 가능합니다',
      price: 1480000,
      photoUrl:
        'https://topcariving.s3.ap-northeast-2.amazonaws.com/ai/daw.jpeg',
    },
  ],
  convenience: [
    {
      carOptionId: 1,
      optionName: '편리 1',
      optionDetail: '높은 토크로 파워풀한 드라이빙이 가능합니다',
      price: 1480000,
      photoUrl:
        'https://topcariving.s3.ap-northeast-2.amazonaws.com/ai/daw.jpeg',
    },
  ],
  multimedia: [
    {
      carOptionId: 1,
      optionName: '멀티미디어 1',
      optionDetail: '높은 토크로 파워풀한 드라이빙이 가능합니다',
      price: 1480000,
      photoUrl:
        'https://topcariving.s3.ap-northeast-2.amazonaws.com/ai/daw.jpeg',
    },
  ],
} as defaultOptionInterface;

//   선택옵션 데이터

const optionDummy: selectOptionInterface[] = [
  {
    carOptionId: 1,
    optionName: '컴포트2',
    price: 6900000,
    photoUrl: 'https://topcariving.s3.ap-northeast-2.amazonaws.com/ai/scc.jpeg',
    details: [
      {
        carOptionId: 1,
        optionName: '후방 주차 충돌방지 보조',
        optionDetail: '주요 주행 정보를 전면 윈드실드에 표시합니다.',
      },
      {
        carOptionId: 2,
        optionName: '후방 주차 충돌방지',
        optionDetail: '주요 주행 정보를 전면 윈드실드에.',
      },
      {
        carOptionId: 3,
        optionName: '후방 주차',
        optionDetail: '주요 주행 정보를 전면.',
      },
      {
        carOptionId: 4,
        optionName: '후방',
        optionDetail: '주요 주행 정보를.',
      },
      {
        carOptionId: 5,
        optionName: '스마트크루즈컨트롤',
        optionDetail: '주요 주행.',
      },
    ],
    tags: [
      {
        tagContent: 'string',
      },
    ],
  },
  {
    carOptionId: 2,
    optionName: '현대스마트센스',
    price: 7900000,
    photoUrl:
      'https://topcariving.s3.ap-northeast-2.amazonaws.com/ai/vibsteeringwheel.jpeg',
    details: [
      {
        carOptionId: 1,
        optionName: '현대스마트센스',
        optionDetail: '주요 주행 정보를 전면 윈드실드에 표시합니다.',
      },
    ],
    tags: [
      {
        tagContent: 'string',
      },
    ],
  },
  {
    carOptionId: 3,
    optionName: '2열 통풍 시트',
    price: 4000000,
    photoUrl:
      'https://topcariving.s3.ap-northeast-2.amazonaws.com/ai/ncss.jpeg',
    details: [
      {
        carOptionId: 1,
        optionName: '2열 통풍 시트',
        optionDetail: '주요 주행 정보를 전면 윈드실드에 표시합니다.',
      },
    ],
    tags: [
      {
        tagContent: 'string',
      },
    ],
  },
  {
    carOptionId: 4,
    optionName: '듀얼 와이드 선루프',
    price: 8900000,
    photoUrl: 'https://topcariving.s3.ap-northeast-2.amazonaws.com/ai/fca.jpeg',
    details: [
      {
        carOptionId: 1,
        optionName: '듀얼 와이드 선루프',
        optionDetail: '주요 주행 정보를 전면 윈드실드에 표시합니다.',
      },
    ],
    tags: [
      {
        tagContent: 'string',
      },
    ],
  },
  {
    carOptionId: 5,
    optionName: '빌트인 캠(보조배터리 포함)',
    price: 6900000,
    photoUrl: 'https://topcariving.s3.ap-northeast-2.amazonaws.com/ai/lka.jpeg',
    details: [
      {
        carOptionId: 1,
        optionName: '빌트인 캠(보조배터리 포함)',
        optionDetail: '주요 주행 정보를 전면 윈드실드에 표시합니다.',
      },
    ],
    tags: [
      {
        tagContent: 'string',
      },
    ],
  },
  {
    carOptionId: 6,
    optionName: '주차보조 시스템 Ⅱ',
    price: 6900000,
    photoUrl: 'https://topcariving.s3.ap-northeast-2.amazonaws.com/ai/bca.jpeg',
    details: [
      {
        carOptionId: 1,
        optionName: '주차보조 시스템 Ⅱ',
        optionDetail: '주차보조 윈드실드에 표시합니다.',
      },
      {
        carOptionId: 2,
        optionName: '제네시스 스마트 크루즈 컨트롤',
        optionDetail: '주요 주행 정보를 전면 윈드실드에 표시합니다.',
      },
    ],
    tags: [
      {
        tagContent: 'string',
      },
    ],
  },
];
