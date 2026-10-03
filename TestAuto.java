package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "Mecanum Box Auto", group = "Auto")
public class Test extends LinearOpMode {

    // Flip any wheel that spins backward
    // when the robot drives forward.
    static final DcMotor.Direction LF_DIR =
            DcMotor.Direction.REVERSE;
    static final DcMotor.Direction LB_DIR =
            DcMotor.Direction.FORWARD;
    static final DcMotor.Direction RF_DIR =
            DcMotor.Direction.FORWARD;
    static final DcMotor.Direction RB_DIR =
            DcMotor.Direction.REVERSE;

    // Motor power, 0.0 to 1.0
    static final double DRIVE_SPEED = 0.4;
    static final double STRAFE_SPEED = 0.6;

    // Inches moved per second at the speeds above.
    // new value = old value * inches it went
    //             / inches it should have gone
    static final double DRIVE_IN_PER_SEC = 16.0;
    static final double STRAFE_IN_PER_SEC = 13.0;

    static final double METER = 39.37; // inches

    private DcMotor leftFront, rightFront;
    private DcMotor leftBack, rightBack;

    @Override
    public void runOpMode() {

        leftFront = hardwareMap.get(
                DcMotor.class, "leftFront");
        rightFront = hardwareMap.get(
                DcMotor.class, "rightFront");
        leftBack = hardwareMap.get(
                DcMotor.class, "leftBack");
        rightBack = hardwareMap.get(
                DcMotor.class, "rightBack");

        leftFront.setDirection(LF_DIR);
        leftBack.setDirection(LB_DIR);
        rightFront.setDirection(RF_DIR);
        rightBack.setDirection(RB_DIR);

        DcMotor.ZeroPowerBehavior brake =
                DcMotor.ZeroPowerBehavior.BRAKE;
        leftFront.setZeroPowerBehavior(brake);
        rightFront.setZeroPowerBehavior(brake);
        leftBack.setZeroPowerBehavior(brake);
        rightBack.setZeroPowerBehavior(brake);

        DcMotor.RunMode mode =
                DcMotor.RunMode.RUN_WITHOUT_ENCODER;
        leftFront.setMode(mode);
        rightFront.setMode(mode);
        leftBack.setMode(mode);
        rightBack.setMode(mode);

        telemetry.addData("Status", "Ready");
        telemetry.update();

        waitForStart();

        forward(METER);
        forward(-METER);   // backward
        strafe(-METER);    // left
        strafe(METER);     // right

        telemetry.addData("Status", "Done");
        telemetry.update();
    }

    // Positive = forward, negative = backward.
    private void forward(double inches) {
        double p = Math.signum(inches) * DRIVE_SPEED;
        double seconds =
                Math.abs(inches) / DRIVE_IN_PER_SEC;
        move(p, p, p, p, seconds);
    }

    // Positive = right, negative = left.
    private void strafe(double inches) {
        double p = Math.signum(inches) * STRAFE_SPEED;
        double seconds =
                Math.abs(inches) / STRAFE_IN_PER_SEC;
        move(p, -p, -p, p, seconds);
    }

    private void move(double lf, double rf,
                      double lb, double rb,
                      double seconds) {
        if (!opModeIsActive()) return;

        leftFront.setPower(lf);
        rightFront.setPower(rf);
        leftBack.setPower(lb);
        rightBack.setPower(rb);

        sleep((long) (seconds * 1000));

        leftFront.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        rightBack.setPower(0);

        sleep(500); // let the robot settle
    }
}
