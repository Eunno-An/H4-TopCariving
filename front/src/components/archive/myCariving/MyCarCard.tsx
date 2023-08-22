import { Flex, Text } from '@components/common';
import styled from '@emotion/styled';
import deleteIcon from '@assets/images/deleteIcon.svg';
import { theme } from '@styles/theme';
import { css } from '@emotion/react';
import { useAlert } from '@contexts/AlertContext';
import { getDate } from '@utils/getDate';
import { MyArchiveUrl, apiInstance } from '@utils/api';

interface CreatedCarTrimInterface {
  바디타입: string;
  모델: string;
  엔진: string;
  구동방식: string;
}

interface CreatedCarSelectedInterface {
  name: string;
  photoUrl: string;
}

interface CreatedCarInterface {
  archivingId: number;
  trims: CreatedCarTrimInterface;
  dayTime: Date;
  selectedOptions: CreatedCarSelectedInterface[];
  complete: boolean;
}

export const MyCarCard = ({
  info,
  deletedCarId,
}: {
  info: CreatedCarInterface;
  deletedCarId: (archivingId: number) => void;
}) => {
  const { openAlert, closeAlert } = useAlert();

  const closeModal = async (archivingId: number) => {
    deletedCarId(archivingId);
    await apiInstance({
      url: `${MyArchiveUrl.DELETE_CARS}/${archivingId}`,
      method: 'DELETE',
    });

    closeAlert();
  };

  const onDeleteHandler = (archivingId: number) => {
    openAlert({
      newContent: [
        '펠리세이드 Le Blacn을',
        '내가 만든 차량 목록에서 삭제하시겠어요?',
      ],
      newButtonInfo: [
        { text: '취소', color: 'LightGray', onClick: closeAlert },
        {
          text: '확인',
          color: 'Primary',
          onClick: () => closeModal(archivingId),
        },
      ],
    });
  };

  return (
    <CarCard>
      <Flex justify="flex-start" gap={3}>
        {!info.complete && (
          <>
            <Text typo="Body4_Medium" alertPalette="Primary">
              *
            </Text>
            <Text typo="Body4_Medium" palette="DarkGray">
              저장하지 않고 나간 차량이 있어요
            </Text>
          </>
        )}
      </Flex>
      <Flex justify="space-between">
        <Text
          typo="Heading4_Bold"
          css={css`
            white-space: nowrap;
          `}
        >
          펠리세이드 Le Blanc
        </Text>
        <Flex gap={10} justify="flex-end">
          {info.complete ? (
            <SaveStatusBox typo="Body4_Medium" isComplete={info.complete}>
              {`${getDate(new Date(info.dayTime))}에 만들었어요`}
            </SaveStatusBox>
          ) : (
            <SaveStatusBox typo="Body4_Medium" isComplete={info.complete}>
              {`${getDate(new Date(info.dayTime))} 임시저장`}
            </SaveStatusBox>
          )}
          <div
            onClick={(e) => {
              e.stopPropagation();
              onDeleteHandler(info.archivingId);
            }}
          >
            <DeleteIcon src={deleteIcon} alt="" />
          </div>
        </Flex>
      </Flex>
      <Flex justify="flex-start">
        <Text typo="Body3_Regular">
          {info.trims.바디타입} / {info.trims.모델} / {info.trims.엔진} /{' '}
          {info.trims.구동방식}
        </Text>
      </Flex>
      <Flex margin="20px 0 0 0">
        {info.selectedOptions.length ? (
          <CarOptionImgBox>
            {info.selectedOptions.map((it, idx) => (
              <ImgContain key={`imgContain_${idx}`}>
                <ImgInfo>
                  <ImgInfoText>{it.name}</ImgInfoText>
                </ImgInfo>
                <ImgBox src={it.photoUrl} alt="" />
              </ImgContain>
            ))}
          </CarOptionImgBox>
        ) : (
          <Flex backgroundColor="LightSand" height={50} borderRadius="8px">
            <Text palette="Sand">선택한 옵션이 없어요</Text>
          </Flex>
        )}
      </Flex>
    </CarCard>
  );
};

const DeleteIcon = styled.img`
  cursor: pointer;
`;

const ImgInfoText = styled.span`
  ${theme.typo.Body4_Regular};
  color: ${theme.palette.White};

  background-color: ${theme.palette.Black};
  padding: 2px 4px;
  border-radius: 4px;

  max-width: 100px;

  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
`;

const ImgContain = styled.div`
  position: relative;
`;
const ImgInfo = styled(Flex)`
  position: absolute;

  justify-content: flex-start;
  top: 93px;
  right: 15px;

  max-width: 100px;
  max-height: 20px;
`;

const CarOptionImgBox = styled(Flex)`
  justify-content: flex-start;

  overflow: scroll;

  gap: 4px;

  height: 140px;

  ::-webkit-scrollbar {
    display: none;
  }
`;

const CarCard = styled(Flex)`
  width: 506px;
  // height: 239px;
  height: auto;

  flex-direction: column;

  border: 2px solid ${theme.palette.Sand};
  border-radius: 8px;

  padding: 20px 30px;

  cursor: pointer;

  &:hover {
    background-color: ${theme.palette.LightSand};
  }
  transition: 0.3s ease;
`;

const SaveStatusBox = styled(Text)<{ isComplete: boolean }>`
  border-radius: 16px;

  background-color: ${({ isComplete }) =>
    isComplete ? theme.palette.LightSand : '#ffd1cd'};
  color: ${({ isComplete }) =>
    isComplete ? theme.palette.Gold : theme.palette.Alert.Primary};

  padding: 2px 12px;
`;

const ImgBox = styled.img`
  width: 122px;
  height: 121px;

  object-fit: cover;

  border-radius: 8px;

  &:hover {
    brightness: 1.2;
  }
  transition: brightness 0.3s ease;
  z-index: 1;
`;
