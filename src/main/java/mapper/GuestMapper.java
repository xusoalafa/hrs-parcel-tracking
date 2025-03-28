package mapper;

import com.hrs.entity.Guest;
import com.hrs.request.GuestCreateRequest;
import com.hrs.response.GuestResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GuestMapper {
  GuestMapper INSTANCE = Mappers.getMapper(GuestMapper.class);

  GuestResponse toGuestResponse(Guest guest);

  @Mapping(target = "id", ignore = true) // Ignore ID during creation
  @Mapping(target = "checkedOut", ignore = true) // Default value: false
  @Mapping(target = "checkedIn", ignore = true) // Default value: false
  @Mapping(target = "parcelAvailable", ignore = true) // Default value: false
  Guest toGuest(GuestCreateRequest guestCreateRequest);
}
