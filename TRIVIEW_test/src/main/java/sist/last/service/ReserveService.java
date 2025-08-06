package sist.last.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import sist.last.dto.ReserveDto;
import sist.last.mapper.ReserveMapperInter;

@Service
public class ReserveService implements ReserveServiceInter {

	@Autowired
	ReserveMapperInter mapper;

	// 트랜잭션 및 비관적 락 정용 예약 메서드 + @Transactional 추가
	@Override
	@Transactional
	public void reservingInsert(ReserveDto dto) {
		Map<String, Object> param = new HashMap<>();
		param.put("room_num", dto.getRoom_num());
		param.put("reserve_checkin", dto.getReserve_checkin());
		param.put("reserve_checkout", dto.getReserve_checkout());

		List<ReserveDto> locked = mapper.getRoomWithLock(param);

		if (!locked.isEmpty()) {
			throw new RuntimeException("이미 예약된 날짜입니다.");
		}

		mapper.reservingInsert(dto);
	}

	@Override
	public ReserveDto getOneDataByIdOfReserve(String info_id, int idx) {
		// TODO Auto-generated method stub
		Map<String, Object> map=new HashMap<>();
		
		map.put("info_id", info_id);
		map.put("idx", idx);
		
		return mapper.getOneDataByIdOfReserve(map);
	}

	@Override
	public int getMaxIdxOfReserve() {
		// TODO Auto-generated method stub
		return mapper.getMaxIdxOfReserve();
	}

	@Override
	public List<ReserveDto> getOneInfoData(String info_id,int accom_num) {
		// TODO Auto-generated method stub
		return mapper.getOneInfoData(info_id, accom_num);
	}

	@Override
    public List<ReserveDto> getReserveCountOfRoomNumber(int room_num) {
        return mapper.getReserveCountOfRoomNumber(room_num);
    }

	@Override
	public List<ReserveDto> getReservationDataById(String info_id) {
		// TODO Auto-generated method stub
		return mapper.getReservationDataById(info_id);
	}

	@Override
	public void updateReserveStatus(ReserveDto dto) {
		// TODO Auto-generated method stub
		mapper.updateReserveStatus(dto);
	}

	@Override
	public String getPhoto(String room_num) {
		// TODO Auto-generated method stub
		return mapper.getPhoto(room_num);
	}
	
}
