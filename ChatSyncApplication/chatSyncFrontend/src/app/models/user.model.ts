export interface User {
    userId?: string,
    username: string,
    password: string,
    email: string,
    firstName: string,
    lastName: string,
    roles?: Array<string>,
    isActive: boolean,
    phoneNo: string,
    profileImage?: string,
    createdAt: Date,
    updatedAt: Date
}