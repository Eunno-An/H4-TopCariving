import { Flex, Text } from '@components/common';

import hyundai from '@assets/images/hyundai.svg';
import cargo from '@assets/images/cargo.svg';
import vector845 from '@assets/images/vector845.svg';
import vector850 from '@assets/images/vector850.svg';
import { css } from '@emotion/react';
import { useAlert } from '@contexts/AlertContext';
import { useNavigate } from 'react-router-dom';
import { LogoutButton } from '@components/common/LogoutButton';
import { cursorPointer } from '@styles/common';

export const Header = () => {
  const { openAlert, closeAlert } = useAlert();

  const navigate = useNavigate();

  const moveToMain = () => {
    navigate('/my-car/trim');
  };

  const moveToArchiving = () => {
    closeAlert();
    navigate('/archive');
  };

  const onClickArchiving = () => {
    let content = [];
    if (window.location.pathname === '/my-car/complete') {
      content = [
        '아카이빙으로 이동할까요?',
        '완성된 차량은 마이카이빙에서 확인할 수 있어요.',
      ];
    } else {
      content = [
        '내 차 만들기를 그만하시겠어요?',
        '만들던 차량은 아카이빙 > 내가 만든 차량에',
        '저장해둘게요',
      ];
    }
    openAlert({
      newContent: content,
      newButtonInfo: [
        { text: '취소', color: 'LightGray', onClick: closeAlert },
        { text: '확인', color: 'Primary', onClick: moveToArchiving },
      ],
    });
  };

  return (
    <Flex
      backgroundColor="Sand"
      height={60}
      justify="center"
      padding="0 100px 0 100px"
      css={css`
        position: fixed;
        top: 0;
        left: 0;
        flex-shrink: 0;
        z-index: 6;
      `}
    >
      <Flex width={1280}>
        <Flex gap={9.58} justify="flex-start">
          <img
            src={hyundai}
            alt="현대자동차 로고"
            css={cursorPointer}
            onClick={moveToMain}
          />
          <img src={vector845} alt="벽" />
          <Text typo="Body4_Medium">내 차 만들기</Text>
        </Flex>

        <Flex justify="flex-end" gap={21}>
          <Flex gap={16} width="auto">
            <Text typo="Body2_Medium" palette="DarkGray">
              팰리세이드
            </Text>
            <img src={vector850} alt="" />
            <Flex
              backgroundColor="Black"
              borderRadius="18px"
              width={98}
              height={35}
              gap={4}
              css={cursorPointer}
              onClick={onClickArchiving}
            >
              <img src={cargo} alt="아카이빙 카고" />
              <Text palette="Sand" typo="Body4_Medium">
                아카이빙
              </Text>
            </Flex>
            <LogoutButton />
          </Flex>
        </Flex>
      </Flex>
    </Flex>
  );
};
