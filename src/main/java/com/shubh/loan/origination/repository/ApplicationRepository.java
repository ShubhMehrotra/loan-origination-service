package com.shubh.loan.origination.repository;

import com.shubh.loan.origination.model.*;
import java.util.*;
import org.springframework.data.jpa.repository.*;

public interface ApplicationRepository extends JpaRepository<Application, UUID> {
}
