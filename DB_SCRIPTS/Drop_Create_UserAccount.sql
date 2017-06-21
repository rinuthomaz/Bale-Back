USE [BRTADEV]
GO

ALTER TABLE [dbo].[UserAccount] DROP CONSTRAINT [FK_UserAccount_User]
GO

ALTER TABLE [dbo].[UserAccount] DROP CONSTRAINT [FK_UserAccount_Role]
GO

ALTER TABLE [dbo].[UserAccount] DROP CONSTRAINT [FK_UserAccount_DeviceType]
GO

ALTER TABLE [dbo].[UserAccount] DROP CONSTRAINT [FK_UserAccount_AccessLevel]
GO

/****** Object:  Table [dbo].[UserAccount]    Script Date: 12/21/2016 3:54:29 PM ******/
DROP TABLE [dbo].[UserAccount]
GO

/****** Object:  Table [dbo].[UserAccount]    Script Date: 12/21/2016 3:54:29 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[UserAccount](
	[AcctId] [int] NOT NULL,
	[UserId] [int] NOT NULL,
	[RoleId] [int] NOT NULL,
	[AccessId] [int] NOT NULL,
	[DeviceTypeId] [int] NOT NULL,
	[Email] [nvarchar](50) NOT NULL,
	[Password] [nvarchar](50) NOT NULL,
	[CreateDate] [smalldatetime] NOT NULL,
	[Enabled] [bit] NOT NULL,
	[UpdatedAt] [smalldatetime] NOT NULL,
	[UpdatedBy] [smalldatetime] NOT NULL,
	[Field1] [nvarchar](50) NULL,
	[Field2] [nvarchar](50) NULL,
 CONSTRAINT [PK_UserAccount] PRIMARY KEY CLUSTERED 
(
	[AcctId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
 CONSTRAINT [IX_UserAccount] UNIQUE NONCLUSTERED 
(
	[Email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

ALTER TABLE [dbo].[UserAccount]  WITH CHECK ADD  CONSTRAINT [FK_UserAccount_AccessLevel] FOREIGN KEY([AccessId])
REFERENCES [dbo].[AccessLevel] ([AccessId])
GO

ALTER TABLE [dbo].[UserAccount] CHECK CONSTRAINT [FK_UserAccount_AccessLevel]
GO

ALTER TABLE [dbo].[UserAccount]  WITH CHECK ADD  CONSTRAINT [FK_UserAccount_DeviceType] FOREIGN KEY([DeviceTypeId])
REFERENCES [dbo].[DeviceType] ([DeviceTypeId])
GO

ALTER TABLE [dbo].[UserAccount] CHECK CONSTRAINT [FK_UserAccount_DeviceType]
GO

ALTER TABLE [dbo].[UserAccount]  WITH CHECK ADD  CONSTRAINT [FK_UserAccount_Role] FOREIGN KEY([RoleId])
REFERENCES [dbo].[Role] ([RoleId])
GO

ALTER TABLE [dbo].[UserAccount] CHECK CONSTRAINT [FK_UserAccount_Role]
GO

ALTER TABLE [dbo].[UserAccount]  WITH CHECK ADD  CONSTRAINT [FK_UserAccount_User] FOREIGN KEY([UserId])
REFERENCES [dbo].[User] ([UserId])
GO

ALTER TABLE [dbo].[UserAccount] CHECK CONSTRAINT [FK_UserAccount_User]
GO

