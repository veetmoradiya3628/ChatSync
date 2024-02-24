export interface UserDto {
    userId?: string;
    username?: string;
    password?: string;
    email?: string;
    firstName?: string;
    lastName?: string;
    isActive?: boolean;
    phoneNo?: string;
    profileImage?: string;
    roles?: Array<string>;
    createdAt?: Date;
    updatedAt?: Date;
}