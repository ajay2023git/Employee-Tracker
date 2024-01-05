import React, { useState } from 'react';
import axios from 'axios';

const EmployeeForm = () => {
  const [employee, setEmployee] = useState({
    employeeId: '',
    firstName: '',
    lastName: '',
    age: '',
    birthDate: '',
    address: '',
    department: '',
    designation: '',
    maritalStatus: '',
    gender: '',
    mobileNumber: '',
  });

  const [errors, setErrors] = useState({});

  const handleChange = (e) => {
    const { name, value } = e.target;
    setEmployee({ ...employee, [name]: value });
    setErrors({ ...errors, [name]: '' }); // Clear previous error for the field
  };

  const validateForm = () => {
    let valid = true;
    const newErrors = {};

    if (!employee.employeeId.trim()) {
      newErrors.employeeId = 'Employee ID is required';
      valid = false;
    }

    if (!employee.firstName.trim()) {
      newErrors.firstName = 'First Name is required';
      valid = false;
    }

    if (!employee.lastName.trim()) {
      newErrors.lastName = 'Last Name is required';
      valid = false;
    }

    if (!employee.gender.trim()) {
      newErrors.gender = 'Gender is required';
      valid = false;
    }

    if (!employee.age.trim() || isNaN(employee.age) || employee.age < 18 || employee.age > 100) {
      newErrors.age = 'Age must be a number between 18 and 100';
      valid = false;
    }

    if (!employee.birthDate.trim()) {
      newErrors.birthDate = 'Birth Date is required';
      valid = false;
    }

    const mobileRegex = /^[6-9]\d{9}$/;
    if (!employee.mobileNumber.trim() || !mobileRegex.test(employee.mobileNumber)) {
      newErrors.mobileNumber = 'Valid 10-digit Mobile Number is required';
      valid = false;
    }

    setErrors(newErrors);
    return valid;
  };

  const handleCreate = async () => {
    if (validateForm()) {
      try {
        await axios.post('http://localhost:8080/employee/create', employee);
        console.log('Employee created successfully');
      } catch (error) {
        console.error('Error creating employee:', error.message);
      }
    }
  };

  const handleRead = async () => {
    try {
      const response = await axios.get('http://localhost:8080/employee/read');
      console.log('Employees:', response.data);
    } catch (error) {
      console.error('Error fetching employees:', error.message);
    }
  };

  const handleUpdate = async () => {
    if (validateForm()) {
      try {
        // Make API request for Update operation
        await axios.put('http://localhost:8080/employee/update/${employee.employeeId', employee);
        console.log('Employee updated successfully');
      } catch (error) {
        console.error('Error updating employee:', error.message);
      }
    }
  };

  return (
    <form>
      <div>
        <label>Employee ID:</label>
        <input type="text" name="employeeId" value={employee.employeeId} onChange={handleChange} />
        <span className="error">{errors.employeeId}</span>
      </div>

      <div>
        <label>First Name:</label>
        <input type="text" name="firstName" value={employee.firstName} onChange={handleChange} />
        <span className="error">{errors.firstName}</span>
      </div>

      <div>
        <label>Last Name:</label>
        <input type="text" name="lastName" value={employee.lastName} onChange={handleChange} />
        <span className="error">{errors.lastName}</span>
      </div>

      <div>
        <label>Gender:</label>
        <input type="text" name="gender" value={employee.gender} onChange={handleChange} />
        <span className="error">{errors.gender}</span>
      </div>

      <div>
        <label>Age:</label>
        <input type="text" name="age" value={employee.age} onChange={handleChange} />
        <span className="error">{errors.age}</span>
      </div>

      <div>
        <label>Birth Date:</label>
        <input type="text" name="birthDate" value={employee.birthDate} onChange={handleChange} />
        <span className="error">{errors.birthDate}</span>
      </div>

      <div>
        <label>Mobile Number:</label>
        <input type="text" name="mobileNumber" value={employee.mobileNumber} onChange={handleChange} />
        <span className="error">{errors.mobileNumber}</span>
      </div>

      <div>
        <label>Address:</label>
        <input type="text" name="address" value={employee.address} onChange={handleChange} />
        {/* Add error span if needed */}
      </div>

      <div>
        <label>Department:</label>
        <input type="text" name="department" value={employee.department} onChange={handleChange} />
        {/* Add error span if needed */}
      </div>

      <div>
        <label>Designation:</label>
        <input type="text" name="designation" value={employee.designation} onChange={handleChange} />
        {/* Add error span if needed */}
      </div>

      <div>
        <label>Marital Status:</label>
        <input type="text" name="maritalStatus" value={employee.maritalStatus} onChange={handleChange} />
        {/* Add error span if needed */}
      </div>

      <div>
        <button type="button" onClick={handleCreate}>
          Create Employee
        </button>
        <button type="button" onClick={handleRead}>
          Read Employees
        </button>
        <button type="button" onClick={handleUpdate}>
          Update Employee
        </button>
      </div>
    </form>
  );
};

export default EmployeeForm;
