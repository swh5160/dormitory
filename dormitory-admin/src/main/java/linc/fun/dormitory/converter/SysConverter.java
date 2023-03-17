package linc.fun.dormitory.converter;

import linc.fun.dormitory.bo.*;
import linc.fun.dormitory.form.*;
import linc.fun.dormitory.po.*;
import linc.fun.dormitory.vo.*;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * @author yqlin
 * @date 2022/4/14 22:13
 * @description MapStruct配置
 */
@Mapper(componentModel = "spring", typeConversionPolicy = ReportingPolicy.ERROR)
public interface SysConverter {
    AdminVO convertToAdminVO(AdminBO adminBO);

    AdminBO convertToAdminBO(Admin admin);

    UserInfoVO convertToUserInfoVO(Student student);

    UserInfoVO convertToUserInfoVO(Admin admin);

    Admin convertToAdmin(AdminForm adminForm);

    BuildingVO convertToBuildingVO(BuildingBO buildingBO);

    StudentVO convertToStudentVO(StudentBO studentBO);

    StudentVO convertToStudentVO(Student student);

    Student convertToStudent(StudentForm studentForm);

    Building convertToBuilding(BuildingForm form);

    BuildingRoomVO convertToBuildingRoomVO(BuildingRoomBO buildingRoomBO);

    List<BuildingVO> convertToBuildingVOList(List<Building> buildingList);

    BuildingRoom convertToBuildingRoom(BuildingRoomForm form);

    NoticeVO convertToNoticeVO(NoticeBO noticeBO);

    NoticeBO convertToNoticeBO(Notice notice);

    Notice convertToNotice(NoticeForm noticeForm);

    StudentSelfInfoVO convertToStudentSelfInfoVO(Student student);

    ProblemVO convertToProblemVO(ProblemBO problemBO);

    Problem convertToProblem(ProblemForm problemForm);

    ProblemBO convertToProblemBO(Problem problem);

    StudentAttendanceVO convertToStudentAttendanceVO(StudentAttendanceBO studentAttendanceBO);


    Student convertToStudent(StudentUpdateSelfForm studentUpdateSelfForm);

}
