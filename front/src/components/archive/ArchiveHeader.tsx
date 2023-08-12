import { Flex, Text } from '@components/common';

import hyundaiBlack from '@assets/images/HyundaiBlack.svg';
import vector845 from '@assets/images/vector845.svg';
import vector850 from '@assets/images/vector850.svg';
import { css } from '@emotion/react';
import { useAlert } from '@contexts/AlertContext';
import { useNavigate } from 'react-router-dom';

export const ArchiveHeader = () => {
  const { openAlert, closeAlert } = useAlert();

  const navigate = useNavigate();
  const moveToArchiving = () => {
    closeAlert();
    navigate('/my-archive');
  };

  const onClickArchiving = () => {
    openAlert({
      newContent: ['마이 카이빙으로 이동하시겠습니까?'],
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
        flex-shrink: 0;
      `}
    >
      <Flex width={1040}>
        <Flex gap={9.58} justify="flex-start">
          <img src={hyundaiBlack} alt="현대자동차 로고" />
          <img src={vector845} alt="벽" />
          <Text typo="Body4_Medium">아카이빙</Text>
        </Flex>

        <Flex justify="flex-end" gap={21}>
          <Flex gap={16} width="auto">
            <Text typo="Heading5_Bold" palette="Black">
              펠리세이드
            </Text>
            <img src={vector850} alt="" />
            <Flex
              backgroundColor="DarkGray"
              borderRadius="4px"
              padding="4px 8px"
              width="auto"
              height="auto"
              gap={4}
              css={css`
                cursor: pointer;
              `}
              onClick={onClickArchiving}
            >
              <Text palette="Neutral" typo="Body2_Medium">
                마이 카이빙
              </Text>
            </Flex>
          </Flex>
        </Flex>
      </Flex>
    </Flex>
  );
};
