import { Button, Flex, Text } from '@components/common';
import styled from '@emotion/styled';
import { theme } from '@styles/theme';
import bookmark from '@assets/images/bookmark.svg';
import {
  ArchiveDetailPageProps,
  archiveDetailInterface,
} from '@pages/Archive/detail';
import { css } from '@emotion/react';
import { ArchiveUrl, apiInstance } from '@utils/api';
import { useToast } from '@contexts/ToastContext';
import { useAlert } from '@contexts/AlertContext';
import { getCarInfo } from '@utils/getCarInfo';
import { useNavigate } from 'react-router-dom';

interface ArchiveShortInfoProps extends ArchiveDetailPageProps {
  getData: () => Promise<void>;
}

export const ArchiveShortInfo = ({
  getData,
  detailInfo,
  optionDetail,
}: ArchiveShortInfoProps) => {
  const { openToast, closeToast } = useToast();
  const onChangeBookMark = async () => {
    closeToast();
    const isBookmark = await apiInstance({
      url: `${ArchiveUrl.BOOKMARK}`,
      method: 'POST',
      bodyData: JSON.stringify({
        archivingId: detailInfo?.archivingId,
        isBookmarked: !detailInfo?.isBookmarked,
      }),
    });
    getData();

    const content = isBookmark.data
      ? '북마크 된 후기는 마이카이빙에서 확인할 수 있어요'
      : '북마크가 해제되었어요';
    openToast({
      newContent: content,
    });
  };

  const { openAlert, closeAlert } = useAlert();
  const onClickStartBtn = () => {
    openAlert({
      newContent: [
        '계속해서 내 차 만들기를 하시겠어요?',
        '내 차 만들기 화면으로 이동해요.',
      ],
      newButtonInfo: [
        { text: '취소', color: 'LightGray', onClick: closeAlert },
        { text: '확인', color: 'Primary', onClick: onStartMakeCar },
      ],
    });
  };

  const navigate = useNavigate();

  const onStartMakeCar = async () => {
    const { data } = await apiInstance({
      url: `${ArchiveUrl.FEED}/${detailInfo?.archivingId}`,
      method: 'POST',
      bodyData: JSON.stringify({
        archivingId: detailInfo?.archivingId,
        isBookmarked: !detailInfo?.isBookmarked,
      }),
    });

    const newInfo = (await apiInstance({
      url: `${ArchiveUrl.DETAIL}/${data}`,
      method: 'GET',
    })) as archiveDetailInterface;

    localStorage.setItem(
      'myCarInfo',
      JSON.stringify(getCarInfo({ info: newInfo })),
    );
    localStorage.setItem('archivingId', data);

    navigate('/my-car/trim');
    closeAlert();
  };

  return (
    <Flex direction="column" justify="flex-start" height="auto">
      <Flex
        direction="column"
        width={1024}
        padding="60px 0 30px 0"
        justify="flex-start"
        gap={47}
      >
        <Flex direction="column" align="flex-start" height="auto">
          <Text typo="Body1_Regular">총 가격</Text>
          <Text typo="Heading1_Bold">{`${detailInfo?.totalPrice.toLocaleString()}원`}</Text>
        </Flex>
        <Flex height="auto" justify="space-between">
          <Flex
            direction="column"
            justify="flex-start"
            align="flex-start"
            height="auto"
            gap={9}
          >
            <Text typo="Body3_Medium" palette="DarkGray">
              선택옵션
            </Text>
            <OptionTagContainer
              gap={8}
              width={546}
              justify="flex-start"
              height="auto"
            >
              {optionDetail?.[`상세 품목`].map((option) => (
                <OptionTag key={`상세품목_${option.carOptionId}`}>
                  <Text typo="Body3_Regular" palette="DarkGray">
                    {option.optionName}
                  </Text>
                </OptionTag>
              ))}
            </OptionTagContainer>
          </Flex>
          <Flex
            gap={14}
            height="auto"
            css={css`
              z-index: 3;
            `}
          >
            <BookMark
              isBookmarked={detailInfo?.isBookmarked}
              onClick={onChangeBookMark}
            >
              <img src={bookmark} alt="" />
            </BookMark>
            <Button
              backgroundColor="Primary"
              padding="16px 71px"
              typo="Heading4_Bold"
              heightType="large"
              onClick={onClickStartBtn}
            >
              이 차량으로 내 차 만들기 시작
            </Button>
          </Flex>
        </Flex>
      </Flex>
      <Flex height={18} backgroundColor="LightSand"></Flex>
    </Flex>
  );
};

const BookMark = styled(Flex)<{ isBookmarked?: boolean }>`
  width: 52px;
  height: 52px;
  border-radius: 50%;
  background-color: ${({ isBookmarked }) =>
    isBookmarked ? theme.palette.Primary : theme.palette.Sand};
  cursor: pointer;
  transition: ease 0.3s;
`;

const OptionTagContainer = styled(Flex)`
  flex-wrap: wrap;
`;

const OptionTag = styled(Flex)`
  width: auto;
  height: 22px;

  padding: 16px 12px;

  border-radius: 4px;
  border: 0.5px solid ${theme.palette.LightGray};
`;
